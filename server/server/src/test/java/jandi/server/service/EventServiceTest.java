package jandi.server.service;

import jandi.server.model.Event;
import jandi.server.model.EventRequestDto;
import jandi.server.model.EventResponseDto;
import jandi.server.model.UserRequestDto;
import jandi.server.repository.EventRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class EventServiceTest {
    @Autowired
    private EventService eventService;
    @Autowired
    private EventRepository eventRepository;

    @AfterEach
    public void afterEach() {

    }

    @Test
    public void create() {
        //given
        List<UserRequestDto> userList = createUserRequestDtos();

        LocalDate started_at = LocalDate.of(2022, 1, 1);
        LocalDate ended_at = LocalDate.of(2022, 12, 31);

        EventRequestDto eventDto = createEventRequestDto("제목1", "내용1", started_at, ended_at, userList);

        //when
        Long event_id = eventService.create(eventDto);

        //then
        Event findEvent = eventService.findOne(event_id);
        assertThat(findEvent.getId()).isEqualTo(event_id);
        assertThat(findEvent.getStarted_at()).isEqualTo(started_at);

    }

    @Test
    public void findCurrentEvents() {
        //given
        createPastAndCurrentEventRequestDto();

        //when
        List<EventResponseDto> eventDtos = eventService.findCurrentEvents();

        //then
        for (EventResponseDto eventDto : eventDtos) {
            assertThat(eventDto.getEnded_at()).isAfter(LocalDate.now());
        }
    }

    @Test
    public void findPastEvents() {
        //given
        createPastAndCurrentEventRequestDto();

        //when
        List<EventResponseDto> eventDtos = eventService.findPastEvents();

        //then
        for (EventResponseDto eventDto : eventDtos) {
            assertThat(eventDto.getEnded_at()).isBefore(LocalDate.now());
        }
    }

    private List<UserRequestDto> createUserRequestDtos() {
        UserRequestDto userDto1 = new UserRequestDto();
        userDto1.setName("장현애1");
        userDto1.setGithub("aeae1");

        UserRequestDto userDto2 = new UserRequestDto();
        userDto1.setName("장현애2");
        userDto1.setGithub("aeae2");

        List<UserRequestDto> userList = new ArrayList<>();
        userList.add(userDto1);
        userList.add(userDto2);
        return userList;
    }

    private EventRequestDto createEventRequestDto(String name, String content, LocalDate start, LocalDate end, List<UserRequestDto> users) {
        EventRequestDto eventDto = new EventRequestDto();
        eventDto.setName(name);
        eventDto.setContent(content);
        eventDto.setStarted_at(start);
        eventDto.setEnded_at(end);
        eventDto.setUsers(users);
        return eventDto;
    }

    private void createPastAndCurrentEventRequestDto() {
        List<UserRequestDto> userList = createUserRequestDtos();

        LocalDate started_at1 = LocalDate.of(2022, 1, 1);
        LocalDate ended_at1 = LocalDate.of(2022, 12, 31);
        EventRequestDto eventDto1 = createEventRequestDto("제목1", "내용1", started_at1, ended_at1, userList);

        LocalDate started_at2 = LocalDate.of(2022, 1, 1);
        LocalDate ended_at2 = LocalDate.of(2022, 1, 2);
        EventRequestDto eventDto2 = createEventRequestDto("제목2", "내용2", started_at2, ended_at2, userList);

        eventService.create(eventDto1);
        eventService.create(eventDto2);
    }

}
