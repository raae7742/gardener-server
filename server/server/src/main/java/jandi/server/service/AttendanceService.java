package jandi.server.service;

import jandi.server.model.AttendOneResponseDto;
import jandi.server.model.AttendTodayResponseDto;
import jandi.server.model.Attendance;
import jandi.server.model.User;
import jandi.server.repository.AttendanceRepository;
import jandi.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    @Transactional
    public Long create(User user) {
        Attendance attendance = new Attendance(user, LocalDate.now());
        attendanceRepository.save(attendance);
        return attendance.getId();
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
