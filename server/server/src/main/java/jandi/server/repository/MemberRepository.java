package jandi.server.repository;

import jandi.server.model.event.Event;
import jandi.server.model.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByEvent(Event event);
}
