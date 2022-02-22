package jandi.server.service;

import jandi.server.model.attendance.dto.AttendTodayResponseDto;
import jandi.server.model.attendance.dto.AttendMemberResponseDto;
import jandi.server.model.event.Event;
import jandi.server.model.event.dto.EventRequestDto;
import jandi.server.model.member.Member;
import jandi.server.model.member.dto.MemberRequestDto;
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
    private MemberService memberService;

    Long event_id;

    @AfterEach
    public void afterEach() {
        eventService.delete(event_id);
    }

    @Test
    public void readOne() {
        //given
        List<MemberRequestDto> userList = createMemberRequestDtos();
        Event event = createEvent(userList);
        List<Member> members = memberService.findByEvent(event);

        //when
        AttendMemberResponseDto attend = attendanceService.readAll(members.get(0));

        //then
        assertThat(attend.getAttendance().size()).isEqualTo(Period.between(event.getStarted_at(), LocalDate.now()).getDays());
    }

    @Test
    public void readToday() {
        //given
        List<MemberRequestDto> memberList = createMemberRequestDtos();
        Event event = createEvent(memberList);
        List<Member> members = memberService.findByEvent(event);

        //when
        AttendTodayResponseDto dto = attendanceService.readToday(event, members.get(0));

        //then
        assertThat(dto.getName()).isEqualTo(members.get(0).getName());
    }


    @Test
    @Transactional
    public void updateCommit() {
        //given
        List<MemberRequestDto> userList = createMemberRequestDtos();
        Event event = createEvent(userList);

        //when
        attendanceService.updateAttendances(event);

        //then

    }

    private List<MemberRequestDto> createMemberRequestDtos() {
        List<MemberRequestDto> memberList = new ArrayList<>();

        MemberRequestDto memberDto1 = new MemberRequestDto();
        memberDto1.setName("장현애");
        memberDto1.setGithub("aeae1");
        memberList.add(memberDto1);

        return memberList;
    }

    private Event createEvent(List<MemberRequestDto> userList) {
        LocalDate started_at = LocalDate.now().minusDays(3);
        LocalDate ended_at = LocalDate.now().plusDays(3);

        EventRequestDto eventDto = createEventRequestDto("제목1", "내용1", started_at, ended_at, userList);
        event_id = eventService.create(eventDto);
        return eventService.findOne(event_id);
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
