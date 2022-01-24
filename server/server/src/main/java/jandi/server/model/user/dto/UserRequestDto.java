package jandi.server.model.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserRequestDto {
    private String name;
    private String password;
    private String github;
}
