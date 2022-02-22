package jandi.server.model.event;

import jandi.server.model.Timestamped;
import jandi.server.model.event.dto.EventRequestDto;
import jandi.server.model.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Event extends Timestamped {

    @Id
    @Column(name = "event_id", length = 20, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "started_at")
    private LocalDate started_at;

    @Column(name = "ended_at")
    private LocalDate ended_at;

    @Column(name = "content", length = 45)
    private String content;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Member> members = new ArrayList<>();

    public Event(EventRequestDto requestDto) {
        this.name = requestDto.getName();
        this.content = requestDto.getContent();
        this.started_at = requestDto.getStarted_at();
        this.ended_at = requestDto.getEnded_at();
    }

    public void update(EventRequestDto requestDto) {
        this.name = requestDto.getName();
        this.content = requestDto.getContent();
        this.started_at = requestDto.getStarted_at();
        this.ended_at = requestDto.getEnded_at();
    }
}
