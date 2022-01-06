package jandi.server.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AttendOneResponseDto {
    private LocalDate date;
    private boolean is_checked;

    public AttendOneResponseDto(Attendance attendance) {
        this.date = attendance.getDate();
        this.is_checked = attendance.is_checked();
    }
}
