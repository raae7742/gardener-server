package jandi.server.model.attendance.dto;

import jandi.server.model.attendance.Attendance;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class AttendOneResponseDto implements Comparable<AttendOneResponseDto> {
    private LocalDate date;
    private boolean is_checked;

    public AttendOneResponseDto(Attendance attendance) {
        this.date = attendance.getDate();
        this.is_checked = attendance.is_checked();
    }

    @Override
    public int compareTo(AttendOneResponseDto other) {
        return this.date.compareTo(other.date);
    }
}
