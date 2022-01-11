package jandi.server.controller;

import jandi.server.model.AttendOneResponseDto;
import jandi.server.model.AttendTodayResponseDto;
import jandi.server.model.Message;
import jandi.server.model.StatusEnum;
import jandi.server.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@CrossOrigin
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping("/checks")
    public ResponseEntity<Message> readCheckByUserId(@RequestParam Long id) {
        List<AttendOneResponseDto> checklist = attendanceService.readOne(id);

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

}
