package jandi.server.model.member;

import jandi.server.model.Timestamped;
import jandi.server.model.attendance.Attendance;
import jandi.server.model.event.Event;
import jandi.server.model.member.dto.MemberRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member extends Timestamped {

    @Id
    @Column(name = "member_id", length = 20)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "github", length = 20)
    private String github;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Attendance> attendances = new ArrayList<>();

    public void setEvent(Event event) {
        this.event = event;
        event.getMembers().add(this);
    }

    public Member(MemberRequestDto requestDto, Event event) {
        this.name = requestDto.getName();
        this.github = requestDto.getGithub();
        setEvent(event);
    }
}
