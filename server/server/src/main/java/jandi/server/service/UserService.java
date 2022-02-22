package jandi.server.service;

import jandi.server.model.user.User;
import jandi.server.model.user.dto.UserLoginDto;
import jandi.server.model.user.dto.UserRequestDto;
import jandi.server.model.user.enums.UserExceptionType;
import jandi.server.repository.UserRepository;
import jandi.server.util.response.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User join(UserRequestDto requestDto) {

        if (validateDuplicateGithub(requestDto.getGithub()))
            throw new CustomException(UserExceptionType.DUPLICATED_USER);

        if (validateDuplicateName(requestDto.getUsername()))
            throw new CustomException(UserExceptionType.DUPLICATED_NAME);

        User user = User.builder()
                .username(requestDto.getUsername())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .github(requestDto.getGithub())
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        return userRepository.save(user);
    }

    public User login(UserLoginDto loginDto) {
        User user = findUserByName(loginDto.getUsername());

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new CustomException(UserExceptionType.WRONG_PASSWORD);
        }
        return user;
    }

    public String updatePassword(String name, String password){
        User user = findUserByName(name);
        String encodedPassword = passwordEncoder.encode(password);
        user.updatePassword(encodedPassword);

        return name;
    }

    public List<User> findAllUsers() { return userRepository.findAll();}

    private User findUserByName(String name) {
        return userRepository.findByUsername(name).orElseThrow(
                () -> new CustomException(UserExceptionType.NOT_FOUND)
        );
    }

    private boolean validateDuplicateGithub(String github) {
        return userRepository.existsByGithub(github);
    }

    private boolean validateDuplicateName(String name) {
        return userRepository.existsByUsername(name);
    }
}
