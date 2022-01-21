package jandi.server.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendTodayResponseDto {
    private String name;
    private String github;
    private boolean is_checked;

    public AttendTodayResponseDto(Attendance attendance) {
        this.name = attendance.getUser().getName();
        this.github = attendance.getUser().getGithub();
        this.is_checked = attendance.is_checked();
    }
}
