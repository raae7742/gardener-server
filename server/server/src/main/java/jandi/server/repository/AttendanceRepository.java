package jandi.server.repository;

import jandi.server.model.Attendance;
import jandi.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByUser(User user);

    List<Attendance> findByDate(LocalDate date);
}
