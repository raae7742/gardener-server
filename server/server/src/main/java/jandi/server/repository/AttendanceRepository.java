package jandi.server.repository;

import jandi.server.model.attendance.Attendance;
import jandi.server.model.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByMember(Member member);

    List<Attendance> findByDate(LocalDate date);

    @Query(value="SELECT * FROM attendance a WHERE member_id = :id AND date = :date", nativeQuery = true)
    Optional<Attendance> findByMemberAndDate(@Param("id") Long id, @Param("date") LocalDate date);

}
