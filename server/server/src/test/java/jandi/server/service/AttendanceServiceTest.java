package jandi.server.service;

import jandi.server.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class AttendanceServiceTest {
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private EventService eventService;
    @Autowired
    private UserService userService;

    Long event_id;

    @AfterEach
    public void afterEach() {
        eventService.delete(event_id);
    }

    @Test
    public void readOne() {
        //given
        List<UserRequestDto> userList = createUserRequestDtos();
        Event event = createEvent(userList);
        List<User> users = userService.findByEvent(event);

        //when
        AttendUserResponseDto attend = attendanceService.readAll(users.get(0));

        //then
        assertThat(attend.getAttendance().size()).isEqualTo(Period.between(event.getStarted_at(), LocalDate.now()).getDays());
    }

    @Test
    public void readToday() {
        //given
        List<UserRequestDto> userList = createUserRequestDtos();
        Event event = createEvent(userList);
        List<User> users = userService.findByEvent(event);

        //when
        AttendTodayResponseDto dto = attendanceService.readToday(users.get(0));

        //then
        assertThat(dto.getUsername()).isEqualTo(users.get(0).getName());
    }


    @Test
    @Transactional
    public void updateCommit() {
        //given
        List<UserRequestDto> userList = createUserRequestDtos();
        Event event = createEvent(userList);

        //when
        attendanceService.updateAttendances(event);

        //then

    }

    private List<UserRequestDto> createUserRequestDtos() {
        List<UserRequestDto> userList = new ArrayList<>();

        UserRequestDto userDto1 = new UserRequestDto();
        userDto1.setName("장현애");
        userDto1.setGithub("aeae1");
        userList.add(userDto1);

        return userList;
    }

    private Event createEvent(List<UserRequestDto> userList) {
        LocalDate started_at = LocalDate.now().minusDays(3);
        LocalDate ended_at = LocalDate.now().plusDays(3);

        EventRequestDto eventDto = createEventRequestDto("제목1", "내용1", started_at, ended_at, userList);
        event_id = eventService.create(eventDto);
        return eventService.findOne(event_id);
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
}
