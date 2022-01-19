package jandi.server.service;

import jandi.server.model.*;
import jandi.server.repository.AttendanceRepository;
import jandi.server.util.github.GithubApi;
import lombok.RequiredArgsConstructor;
import org.kohsuke.github.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final GithubApi githubApi = new GithubApi();

    @Transactional
    public Attendance create(User user, LocalDate date) {
        Attendance attendance = new Attendance(user, date);
        attendanceRepository.save(attendance);
        return attendance;
    }

    @Transactional
    public void updateAttendances(Event event) {
        LocalDate started_at = event.getStarted_at();
        LocalDate ended_at = event.getEnded_at();
        for (User user : event.getUsers()) {
            List<Attendance> attendances = attendanceRepository.findByUser(user);
            updateCommit(user, started_at, ended_at);
            //updateTIL(user);
        }

    }

    @Transactional
    private void updateCommit(User user, LocalDate started_at, LocalDate ended_at) {
        PagedIterator<GHCommit> iterator = githubApi.getCommits(user.getGithub());
        List<Attendance> attendances = attendanceRepository.findByUser(user);

        try {
            while (iterator.hasNext()) {
                GHCommit commit = iterator.next();
                LocalDate date = commit.getCommitDate().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                if (date.isBefore(started_at)) break;
                if (date.isBefore(ended_at)) {
                    Attendance attendance = null;
                    for (Attendance a : attendances)
                        if (a.getDate().isEqual(date)) {
                            attendance = a;
                            if (!attendance.isCommit()) attendance.setCommitOn();
                        }

                    if (attendance == null) {
                        attendance = create(user, date);
                        attendance.setCommitOn();
                        attendances = attendanceRepository.findByUser(user);
                    }
                }
            }
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
    }

    public void updateTIL(User user) {

    }

    @Transactional
    public AttendUserResponseDto readAll(User user) {
        AttendUserResponseDto response = new AttendUserResponseDto(user);

        List<Attendance> list = attendanceRepository.findByUser(user);
        for (Attendance a : list) {
            response.getAttendance().add(new AttendOneResponseDto(a));
        }

        Collections.sort(response.getAttendance());
        return response;
    }

    @Transactional
    public AttendTodayResponseDto readToday(User user) {
        Attendance attendance = attendanceRepository.findByUserAndDate(user.getId(), LocalDate.now())
                .orElseGet(()->create(user, LocalDate.now()));

        return new AttendTodayResponseDto(attendance);
    }

    @Transactional
    public void delete(Long id) {
        Attendance attendance = attendanceRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("ID가 존재하지 않습니다.")
        );
        attendanceRepository.delete(attendance);
    }
}
