package jandi.server.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jandi.server.model.user.dto.UserRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "user_id", length = 20)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "github", length = 20)
    private String github;

    public User(UserRequestDto requestDto) {
        this.password = requestDto.getPassword();
        this.name = requestDto.getName();
        this.github = requestDto.getGithub();
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}