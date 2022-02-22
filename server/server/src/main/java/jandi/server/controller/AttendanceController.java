package jandi.server.controller;

import jandi.server.model.*;
import jandi.server.model.attendance.dto.AttendTodayResponseDto;
import jandi.server.model.attendance.dto.AttendMemberResponseDto;
import jandi.server.model.event.Event;
import jandi.server.model.member.Member;
import jandi.server.service.AttendanceService;
import jandi.server.service.EventService;
import jandi.server.service.MemberService;
import jandi.server.util.response.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final EventService eventService;
    private final MemberService memberService;

    @GetMapping("/checks")
    public ResponseEntity<Message> readAllCheck(@RequestParam Long event_id) {
        Event event = eventService.findOne(event_id);
        //attendanceService.updateAttendances(event);

        List<Member> members = memberService.findByEvent(event);
        List<AttendMemberResponseDto> allAttendList = new ArrayList<>();

        for (Member member : members) {
            allAttendList.add(attendanceService.readAll(member));
        }

        Message message = Message.builder()
                .message("성공")
                .status(StatusEnum.OK)
                .data(allAttendList)
                .build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/checks/today")
    public ResponseEntity<Message> readTodayCheck(@RequestParam Long event_id) {
        Event event = eventService.findOne(event_id);
        attendanceService.updateAttendances(event);         // 커밋 히스토리 업데이트

        List<Member> members = memberService.findByEvent(event);
        List<AttendTodayResponseDto> todayAttendList = new ArrayList<>();

        for (Member member : members) {
            todayAttendList.add(attendanceService.readToday(event, member));
        }

        Message message = Message.builder()
                .message("성공")
                .status(StatusEnum.OK)
                .data(todayAttendList)
                .build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
