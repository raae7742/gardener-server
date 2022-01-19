package jandi.server.repository;

import jandi.server.model.Attendance;
import jandi.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByUser(User user);

    List<Attendance> findByDate(LocalDate date);

    @Query(value="SELECT * FROM attendance a WHERE user_id = :id AND date = :date", nativeQuery = true)
    Optional<Attendance> findByUserAndDate(@Param("id") Long id, @Param("date") LocalDate date);

}
