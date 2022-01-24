package jandi.server.controller;

import jandi.server.model.StatusEnum;
import jandi.server.model.user.dto.UserLoginDto;
import jandi.server.model.user.dto.UserRequestDto;
import jandi.server.service.UserService;
import jandi.server.util.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping("/users")
    public ResponseEntity<Message> join(@RequestBody UserRequestDto requestDto) {
        Message message = Message.builder()
                .status(StatusEnum.OK)
                .message("성공")
                .data(userService.signUp(requestDto))
                .build();

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<Message> login(@RequestBody UserLoginDto loginDto) {
        Message message = Message.builder()
                .status(StatusEnum.OK)
                .message("성공")
                .data(userService.signIn(loginDto))
                .build();

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // 패스워드 변경

    // 내 이벤트 조회
}
