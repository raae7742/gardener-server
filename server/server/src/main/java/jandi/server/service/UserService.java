package jandi.server.service;

import jandi.server.model.user.User;
import jandi.server.model.user.dto.UserLoginDto;
import jandi.server.model.user.dto.UserRequestDto;
import jandi.server.model.user.enums.UserExceptionType;
import jandi.server.repository.UserRepository;
import jandi.server.util.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User signUp(UserRequestDto requestDto) {
        if (validateDuplicateGithub(requestDto.getGithub()))
            throw new CustomException(UserExceptionType.DUPLICATED_USER);
        if (validateDuplicateName(requestDto.getName()))
            throw new CustomException(UserExceptionType.DUPLICATED_NAME);

        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        return userRepository.save(new User(requestDto));
    }

    public User signIn(UserLoginDto loginDto) {
        User user = findOne(loginDto.getName());
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new CustomException(UserExceptionType.WRONG_PASSWORD);
        }

        return user;
    }

    public String updatePassword(String name, String password){
        User user = findOne(name);
        String encodedPassword = passwordEncoder.encode(password);
        user.updatePassword(encodedPassword);

        return name;
    }

    public List<User> findUsers() { return userRepository.findAll();}

    private User findOne(String name) {
        return userRepository.findByName(name).orElseThrow(
                () -> new CustomException(UserExceptionType.NOT_FOUND)
        );
    }

    private boolean validateDuplicateGithub(String github) {
        return userRepository.existsByGithub(github);
    }

    private boolean validateDuplicateName(String name) {
        return userRepository.existsByName(name);
    }
}
