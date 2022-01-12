package jandi.server.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserResponseDto {
    private Long id;

    private String name;

    private String github;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.github = user.getGithub();
    }
}
