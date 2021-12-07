package jandi.server.repository;

import jandi.server.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value="SELECT * FROM event where ended_time > 현재 시간")
    public List<Event> findCurrentdEvents();

    @Query(value="SELECT * FROM event where ended_time < 현재 시간")
    public List<Event> findPastEvents();

    Optional<Event> findById(String id);
}
