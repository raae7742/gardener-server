package jandi.server.controller;

import jandi.server.model.*;
import jandi.server.service.AttendanceService;
import jandi.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.kohsuke.github.GHCommit;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
@CrossOrigin
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final UserService userService;

    @GetMapping("/checks")
    public ResponseEntity<Message> readCheckByUserId(@RequestParam Long id) {
        User user = userService.findOne(id);
        List<AttendOneResponseDto> checklist = attendanceService.readOne(user);

        Message message = Message.builder()
                .message("성공")
                .status(StatusEnum.OK)
                .data(checklist)
                .build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/checks/today")
    public ResponseEntity<Message> readTodayAllCheck() {
        List<AttendTodayResponseDto> checklist = attendanceService.readToday();

        Message message = Message.builder()
                .message("성공")
                .status(StatusEnum.OK)
                .data(checklist)
                .build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("checks/commit")
    public ResponseEntity<Message> readCommits(@RequestParam Long userId) throws IOException {
        User user = userService.findOne(userId);
        attendanceService.readCommitHistory(user);

        Message message = Message.builder()
                .message("성공")
                .status(StatusEnum.OK)
                .build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
