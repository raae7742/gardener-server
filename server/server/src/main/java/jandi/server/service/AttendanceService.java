package jandi.server.service;

import jandi.server.model.attendance.dto.AttendOneResponseDto;
import jandi.server.model.attendance.dto.AttendTodayResponseDto;
import jandi.server.model.attendance.dto.AttendMemberResponseDto;
import jandi.server.model.attendance.Attendance;
import jandi.server.model.attendance.enums.AttendanceExceptionType;
import jandi.server.model.event.Event;
import jandi.server.model.member.Member;
import jandi.server.repository.AttendanceRepository;
import jandi.server.util.CommitUtil;
import jandi.server.util.response.CustomException;
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
    private final CommitUtil commitUtil = new CommitUtil();

    public Attendance create(Member member, LocalDate date) {
        Attendance attendance = new Attendance(member, date);
        attendanceRepository.save(attendance);
        return attendance;
    }

    public void updateAttendances(Event event) {
        for (Member member : event.getMembers()) {
            createDate(member);
            updateCommit(member, event);
            //updateTIL(member);
        }
    }

    private void createDate(Member member) {
        List<Attendance> attendances = attendanceRepository.findByMember(member);
        Collections.sort(attendances);

        LocalDate finalDate = attendances.get(attendances.size()-1).getDate();
        while (!finalDate.isEqual(LocalDate.now())) {
            attendances.add(attendances.size(), create(member, finalDate.plusDays(1)));
            finalDate = finalDate.plusDays(1);
        }
    }

    private void updateCommit(Member member, Event event) {
        LocalDate started_at = event.getStarted_at();
        LocalDate ended_at = event.getEnded_at();

        List<Attendance> attendances = attendanceRepository.findByMember(member);

        PagedIterator<GHCommit> iterator = commitUtil.getCommits(member.getGithub());
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
            throw new CustomException(AttendanceExceptionType.COMMIT_ERROR);
        }
    }

    public void updateTIL(Member member) {

    }

    public AttendMemberResponseDto readAll(Member member) {
        AttendMemberResponseDto response = new AttendMemberResponseDto(member);

        List<Attendance> list = attendanceRepository.findByMember(member);
        for (Attendance a : list) {
            response.getAttendance().add(new AttendOneResponseDto(a));
        }

        Collections.sort(response.getAttendance());
        return response;
    }

    public AttendTodayResponseDto readToday(Event event, Member member) {
        LocalDate started_at = event.getStarted_at();
        LocalDate ended_at = event.getEnded_at();
        Attendance attendance;

        if (ended_at.isAfter(LocalDate.now())) {
            attendance = attendanceRepository.findByMemberAndDate(member.getId(), ended_at)
                    .orElseGet(() -> create(member, LocalDate.now()));
        }
        else if (started_at.isBefore(LocalDate.now())) {
            throw new CustomException(AttendanceExceptionType.NOT_STARTED);
        }
        else {
            attendance = attendanceRepository.findByMemberAndDate(member.getId(), LocalDate.now())
                    .orElseGet(()->create(member, LocalDate.now()));
        }

        return new AttendTodayResponseDto(attendance);
    }

    public void delete(Long id) {
        Attendance attendance = attendanceRepository.findById(id).orElseThrow(
                () -> new CustomException(AttendanceExceptionType.NOT_FOUND)
        );
        attendanceRepository.delete(attendance);
    }
}
