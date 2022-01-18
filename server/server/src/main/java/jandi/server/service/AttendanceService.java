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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final GithubApi githubApi = new GithubApi();

    @Transactional
    public Long create(User user) {
        Attendance attendance = new Attendance(user, LocalDate.now());
        attendanceRepository.save(attendance);
        return attendance.getId();
    }

    public void readCommitHistory(User user) throws IOException {
        Event event = user.getEvent();
        Date started_at = Date.from(event.getStarted_at().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date ended_at = Date.from(event.getEnded_at().atStartOfDay(ZoneId.systemDefault()).toInstant());

        PagedIterator<GHCommit> iterator = githubApi.getCommits("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", user.getGithub());
        List<Attendance> list = attendanceRepository.findByUser(user);

        while (iterator.hasNext()) {
            GHCommit commit = iterator.next();
            Date date = commit.getCommitDate();

            if (commit.getCommitDate().before(started_at)) break;
            else if (commit.getCommitDate().before(ended_at)) {
                // attendance 갱신

            }
        }

    }

    public void updateTIL(User user) {

    }

    @Transactional
    public List<AttendOneResponseDto> readOne(User user) {
        List<Attendance> list = attendanceRepository.findByUser(user);
        List<AttendOneResponseDto> response = new ArrayList<>();
        for (Attendance a : list) {
            response.add(new AttendOneResponseDto(a));
        }
        return response;
    }

    @Transactional
    public List<AttendTodayResponseDto> readToday() {
        List<Attendance> list = attendanceRepository.findByDate(LocalDate.now());
        List<AttendTodayResponseDto> response = new ArrayList<>();
        for (Attendance a : list) {
            response.add(new AttendTodayResponseDto(a));
        }
        return response;
    }

    @Transactional
    public void delete(Long id) {
        Attendance attendance = attendanceRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("ID가 존재하지 않습니다.")
        );
        attendanceRepository.delete(attendance);
    }
}
