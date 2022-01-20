package jandi.server.repository;

import jandi.server.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value="SELECT * FROM event e WHERE ended_at > now() AND started_at < now()", nativeQuery = true)
    List<Event> findCurrentEvents();

    @Query(value="SELECT * FROM event e WHERE ended_at < now()", nativeQuery = true)
    List<Event> findPastEvents();

    @Query(value="SELECT * FROM event e WHERE started_at > now()", nativeQuery = true)
    List<Event> findFutureEvents();
}
