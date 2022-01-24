package jandi.server.model.attendance.dto;

import jandi.server.model.attendance.Attendance;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendTodayResponseDto {
    private String name;
    private String github;
    private boolean is_checked;

    public AttendTodayResponseDto(Attendance attendance) {
        this.name = attendance.getMember().getName();
        this.github = attendance.getMember().getGithub();
        this.is_checked = attendance.is_checked();
    }
}
