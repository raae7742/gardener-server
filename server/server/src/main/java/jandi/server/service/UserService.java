package jandi.server.service;

import jandi.server.model.Event;
import jandi.server.model.User;
import jandi.server.model.UserRequestDto;
import jandi.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void create(UserRequestDto requestDto, Event event) {
        userRepository.save(new User(requestDto, event));
    }

    @Transactional
    public User findOne(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
    }

    @Transactional
    public List<User> findByEvent(Event event) {
        return userRepository.findByEvent(event);
    }

    public void delete(Long id) {
        User user = findOne(id);
        userRepository.delete(user);
    }
}
