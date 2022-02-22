package jandi.server.service;

import jandi.server.model.event.Event;
import jandi.server.model.event.dto.EventRequestDto;
import jandi.server.model.event.dto.EventResponseDto;
import jandi.server.model.member.dto.MemberRequestDto;
import jandi.server.repository.EventRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class EventServiceTest {
    @Autowired
    private EventService eventService;
    @Autowired
    private EventRepository eventRepository;

    Stack<Long> eventStack = new Stack<>();

    @AfterEach
    public void afterEach() {
        while(!eventStack.isEmpty())
            eventService.delete(eventStack.pop());
    }

    @Test
    public void create() {
        //given
        List<MemberRequestDto> userList = createMemberRequestDtos();

        LocalDate started_at = LocalDate.of(2022, 1, 1);
        LocalDate ended_at = LocalDate.of(2022, 1, 2);

        EventRequestDto eventDto = createEventRequestDto("제목1", "내용1", started_at, ended_at, userList);

        //when
        Long event_id = eventService.create(eventDto);
        eventStack.add(event_id);

        //then
        Event findEvent = eventService.findOne(event_id);
        assertThat(findEvent.getId()).isEqualTo(event_id);
        assertThat(findEvent.getStarted_at()).isEqualTo(started_at);

    }

    @Test
    public void findCurrentEvents() {
        //given
        createPastEvent();
        createCurrentEvent();

        //when
        List<EventResponseDto> eventDtos = eventService.findCurrentEvents();

        //then
        for (EventResponseDto eventDto : eventDtos) {
            assertThat(eventDto.getEnded_at()).isAfterOrEqualTo(LocalDate.now());
        }
    }

    @Test
    public void findPastEvents() {
        //given
        createPastEvent();
        createCurrentEvent();

        //when
        List<EventResponseDto> eventDtos = eventService.findPastEvents();

        //then
        for (EventResponseDto eventDto : eventDtos) {
            assertThat(eventDto.getEnded_at()).isBefore(LocalDate.now());
        }
    }

    private List<MemberRequestDto> createMemberRequestDtos() {
        MemberRequestDto memberDto = new MemberRequestDto();
        memberDto.setName("장현애");
        memberDto.setGithub("raae7742");

        List<MemberRequestDto> memberList = new ArrayList<>();
        memberList.add(memberDto);
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

    private void createPastEvent() {
        List<MemberRequestDto> memberList = createMemberRequestDtos();
        LocalDate started_at = LocalDate.now().minusDays(2);
        LocalDate ended_at = LocalDate.now().plusDays(2);
        EventRequestDto eventDto = createEventRequestDto("제목1", "내용1", started_at, ended_at, memberList);
        eventStack.add(eventService.create(eventDto));
    }

    private void createCurrentEvent() {
        List<MemberRequestDto> memberList = createMemberRequestDtos();
        LocalDate started_at = LocalDate.now().minusDays(3);
        LocalDate ended_at = LocalDate.now().minusDays(1);
        EventRequestDto eventDto = createEventRequestDto("제목2", "내용2", started_at, ended_at, memberList);
        eventStack.add(eventService.create(eventDto));
    }
}
