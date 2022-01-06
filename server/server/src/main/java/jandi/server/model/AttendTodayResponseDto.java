package jandi.server.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendTodayResponseDto {
    private Long user_id;
    private boolean is_checked;

    public AttendTodayResponseDto(Attendance attendance) {
        this.user_id = attendance.getUser().getId();
        this.is_checked = attendance.is_checked();
    }
}
