package jandi.server.controller;

import jandi.server.model.*;
import jandi.server.service.AttendanceService;
import jandi.server.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final AttendanceService attendanceService;

    @PostMapping("/events")
    public ResponseEntity<Message> create(@RequestBody EventRequestDto requestDto) {
        Long id = eventService.create(requestDto);
        attendanceService.updateAttendances(eventService.findOne(id));

        Message message = Message.builder()
                .message("성공")
                .status(StatusEnum.OK)
                .data(id)
                .build();

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/events")
    public ResponseEntity<Message> readCurrentEvents() {
        Message message = Message.builder()
                .message("성공")
                .status(StatusEnum.OK)
                .data(eventService.findCurrentEvents())
                .build();

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/events/past")
    public ResponseEntity<Message> readPastEvents() {
        Message message = Message.builder()
                .message("성공")
                .status(StatusEnum.OK)
                .data(eventService.findPastEvents())
                .build();

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/events")
    public ResponseEntity<Message> delete(@RequestParam Long id) {
        Message message = Message.builder()
                .message("성공")
                .status(StatusEnum.OK)
                .data(eventService.delete(id))
                .build();

        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
