package jandi.server.model.attendance.dto;

import jandi.server.model.member.Member;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AttendMemberResponseDto {
    private String username;
    private List<AttendOneResponseDto> attendance = new ArrayList<>();

    public AttendMemberResponseDto(Member member) {
        this.username = member.getName();
    }
}
