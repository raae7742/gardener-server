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
import java.time.Period;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final GithubApi githubApi = new GithubApi();

    public Attendance create(User user, LocalDate date) {
        Attendance attendance = new Attendance(user, date);
        attendanceRepository.save(attendance);
        return attendance;
    }

    public void updateAttendances(Event event) {
        for (User user : event.getUsers()) {
            updateDate(user);
            updateCommit(user, event);
            //updateTIL(user);
        }

    }

    private void updateDate(User user) {
        List<Attendance> attendances = attendanceRepository.findByUser(user);
        Collections.sort(attendances);

        LocalDate finalDate = attendances.get(attendances.size()-1).getDate();
        while (!finalDate.isEqual(LocalDate.now())) {
            attendances.add(attendances.size(), create(user, finalDate.plusDays(1)));
            finalDate = finalDate.plusDays(1);
        }
    }

    private void updateCommit(User user, Event event) {
        LocalDate started_at = event.getStarted_at();
        LocalDate ended_at = event.getEnded_at();

        List<Attendance> attendances = attendanceRepository.findByUser(user);

        PagedIterator<GHCommit> iterator = githubApi.getCommits(user.getGithub());
        try {
            while (iterator.hasNext()) {
                GHCommit commit = iterator.next();
                LocalDate date = commit.getCommitDate().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                if (date.isBefore(started_at)) break;
                if (date.isBefore(ended_at)) {
                    Attendance a = attendances.get(Period.between(started_at, date).getDays());
                    if (!a.isCommit()) a.setCommitOn();
                }
            }
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
    }

    public void updateTIL(User user) {

    }

    public AttendUserResponseDto readAll(User user) {
        AttendUserResponseDto response = new AttendUserResponseDto(user);

        List<Attendance> list = attendanceRepository.findByUser(user);
        for (Attendance a : list) {
            response.getAttendance().add(new AttendOneResponseDto(a));
        }

        Collections.sort(response.getAttendance());
        return response;
    }

    public AttendTodayResponseDto readToday(User user) {
        Attendance attendance = attendanceRepository.findByUserAndDate(user.getId(), LocalDate.now())
                .orElseGet(()->create(user, LocalDate.now()));

        return new AttendTodayResponseDto(attendance);
    }

    public void delete(Long id) {
        Attendance attendance = attendanceRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("ID가 존재하지 않습니다.")
        );
        attendanceRepository.delete(attendance);
    }
}
