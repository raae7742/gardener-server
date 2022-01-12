package jandi.server.service;

import jandi.server.model.Event;
import jandi.server.model.EventRequestDto;
import jandi.server.model.User;
import jandi.server.model.UserRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UserServiceTest {

    @Autowired UserService userService;
    @Autowired EventService eventService;

    String user_name = "장현애";
    String user_github = "aeae1";
    Long event_id;

    @AfterEach
    public void afterEach() {
        eventService.delete(event_id);
    }

    @Test
    public void create() {
        //given
        List<UserRequestDto> userList = createUserRequestDtos();
        LocalDate started_at = LocalDate.of(2022, 1, 1);
        LocalDate ended_at = LocalDate.of(2022, 12, 31);

        EventRequestDto eventDto = createEventRequestDto("제목1", "내용1", started_at, ended_at, userList);
        event_id = eventService.create(eventDto);
        Event event = eventService.findOne(event_id);

        //when
        List<User> users = userService.findByEvent(event);

        //then
        Assertions.assertThat(users.get(0).getName()).isEqualTo(user_name);
        Assertions.assertThat(users.get(0).getGithub()).isEqualTo(user_github);

    }

    private List<UserRequestDto> createUserRequestDtos() {
        List<UserRequestDto> userList = new ArrayList<>();

        UserRequestDto userDto1 = new UserRequestDto();
        userDto1.setName(user_name);
        userDto1.setGithub(user_github);
        userList.add(userDto1);
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

}
