package jandi.server.model.member.dto;

import jandi.server.model.member.Member;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberResponseDto {
    private Long id;

    private String name;

    private String github;

    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.github = member.getGithub();
    }
}
