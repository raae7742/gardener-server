package jandi.server.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendTodayResponseDto {
    private String profileImage;
    private String username;
    private boolean is_checked;

    public AttendTodayResponseDto(Attendance attendance) {
        this.username = attendance.getUser().getName();
        this.is_checked = attendance.is_checked();
    }
}
