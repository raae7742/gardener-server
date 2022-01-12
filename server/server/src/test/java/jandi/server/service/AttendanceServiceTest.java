package jandi.server.service;

import jandi.server.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
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
        List<User> users = createUsersandAttendance(userList);

        //when
        List<AttendOneResponseDto> list = attendanceService.readOne(users.get(0));

        //then
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getDate()).isEqualTo(LocalDate.now());
    }

    @Test
    public void readToday() {
        //given
        List<UserRequestDto> userList = createUserRequestDtos();
        List<User> users = createUsersandAttendance(userList);

        //when
        List<AttendTodayResponseDto> list = attendanceService.readToday();

        //then
        assertThat(list.size()).isEqualTo(2);
    }

    private List<UserRequestDto> createUserRequestDtos() {
        List<UserRequestDto> userList = new ArrayList<>();

        UserRequestDto userDto1 = new UserRequestDto();
        userDto1.setName("장현애1");
        userDto1.setGithub("aeae1");
        userList.add(userDto1);

        UserRequestDto userDto2 = new UserRequestDto();
        userDto1.setName("장현애2");
        userDto1.setGithub("aeae2");
        userList.add(userDto2);

        return userList;
    }

    private List<User> createUsersandAttendance(List<UserRequestDto> userList) {
        LocalDate started_at = LocalDate.of(2022, 1, 1);
        LocalDate ended_at = LocalDate.of(2022, 12, 31);

        EventRequestDto eventDto = createEventRequestDto("제목1", "내용1", started_at, ended_at, userList);
        event_id = eventService.create(eventDto);
        Event event = eventService.findOne(event_id);

        List<User> users = userService.findByEvent(event);
        attendanceService.create(users.get(0));
        attendanceService.create(users.get(1));
        return users;
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
