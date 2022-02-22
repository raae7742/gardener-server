package jandi.server.service;

import jandi.server.model.event.Event;
import jandi.server.model.member.Member;
import jandi.server.model.member.dto.MemberRequestDto;
import jandi.server.model.member.enums.MemberExceptionType;
import jandi.server.repository.MemberRepository;
import jandi.server.util.response.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public Member create(MemberRequestDto requestDto, Event event) {
        return memberRepository.save(new Member(requestDto, event));
    }

    public Member findOne(Long id) {
        return memberRepository.findById(id).orElseThrow(() ->
                new CustomException(MemberExceptionType.NOT_FOUND)
        );
    }

    public List<Member> findByEvent(Event event) {
        return memberRepository.findByEvent(event);
    }

    public void delete(Long id) {
        Member member = findOne(id);
        memberRepository.delete(member);
    }
}
