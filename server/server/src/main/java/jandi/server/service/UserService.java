package jandi.server.service;

import jandi.server.model.Event;
import jandi.server.model.User;
import jandi.server.model.UserRequestDto;
import jandi.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AttendanceService attendanceService;

    public void create(UserRequestDto requestDto, Event event) {
        userRepository.save(new User(requestDto, event));
    }

    public User findOne(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        return user;
    }

    public void delete(Event event) {
        List<User> users = userRepository.findByEvent(event);
        for (User user : users) {
            attendanceService.delete(user);
            userRepository.delete(user);
        }
    }
}
