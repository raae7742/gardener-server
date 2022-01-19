package jandi.server.controller;

import jandi.server.model.*;
import jandi.server.service.AttendanceService;
import jandi.server.service.EventService;
import jandi.server.service.UserService;
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
    private final UserService userService;

    @GetMapping("/checks")
    public ResponseEntity<Message> readAllCheck(@RequestParam Long event_id) {
        Event event = eventService.findOne(event_id);
        //attendanceService.updateAttendances(event);

        List<User> users = userService.findByEvent(event);
        List<AttendUserResponseDto> allAttendList = new ArrayList<>();

        for (User user : users) {
            allAttendList.add(attendanceService.readAll(user));
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

        List<User> users = userService.findByEvent(event);
        List<AttendTodayResponseDto> todayAttendList = new ArrayList<>();

        for (User user : users) {
            todayAttendList.add(attendanceService.readToday(user));
        }

        Message message = Message.builder()
                .message("성공")
                .status(StatusEnum.OK)
                .data(todayAttendList)
                .build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
