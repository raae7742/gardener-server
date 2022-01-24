package jandi.server.service;

import jandi.server.model.event.Event;
import jandi.server.model.event.dto.EventRequestDto;
import jandi.server.model.member.Member;
import jandi.server.model.member.dto.MemberRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired EventService eventService;

    String member_name = "장현애";
    String member_github = "aeae1";
    Long event_id;

    @AfterEach
    public void afterEach() {
        eventService.delete(event_id);
    }

    @Test
    public void create() {
        //given
        List<MemberRequestDto> memberList = createMemberRequestDtos();
        LocalDate started_at = LocalDate.of(2022, 1, 1);
        LocalDate ended_at = LocalDate.of(2022, 1, 2);

        EventRequestDto eventDto = createEventRequestDto("제목1", "내용1", started_at, ended_at, memberList);
        event_id = eventService.create(eventDto);
        Event event = eventService.findOne(event_id);

        //when
        List<Member> members = memberService.findByEvent(event);

        //then
        Assertions.assertThat(members.get(0).getName()).isEqualTo(member_name);
        Assertions.assertThat(members.get(0).getGithub()).isEqualTo(member_github);

    }

    private List<MemberRequestDto> createMemberRequestDtos() {
        List<MemberRequestDto> memberList = new ArrayList<>();

        MemberRequestDto memberDto1 = new MemberRequestDto();
        memberDto1.setName(member_name);
        memberDto1.setGithub(member_github);
        memberList.add(memberDto1);
        return memberList;
    }

    private EventRequestDto createEventRequestDto(String name, String content, LocalDate start, LocalDate end, List<MemberRequestDto> members) {
        EventRequestDto eventDto = new EventRequestDto();
        eventDto.setName(name);
        eventDto.setContent(content);
        eventDto.setStarted_at(start);
        eventDto.setEnded_at(end);
        eventDto.setMembers(members);
        return eventDto;
    }

}
