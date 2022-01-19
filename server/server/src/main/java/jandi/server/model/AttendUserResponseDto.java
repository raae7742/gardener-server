package jandi.server.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AttendUserResponseDto {
    private String username;
    private List<AttendOneResponseDto> attendance = new ArrayList<>();

    public AttendUserResponseDto(User user) {
        this.username = user.getName();
    }
}
