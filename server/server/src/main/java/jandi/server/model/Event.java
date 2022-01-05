package jandi.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Event extends Timestamped{

    @Id
    @Column(name = "event_id", length = 20)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "started_at")
    private LocalDate started_at;

    @Column(name = "ended_at")
    private LocalDate ended_at;

    @Column(name = "content", length = 45)
    private String content;

    @OneToMany(mappedBy = "event")
    private List<User> users = new ArrayList<>();

    public Event(EventRequestDto requestDto) {
        this.name = requestDto.getName();
        this.content = requestDto.getContent();
        this.started_at = requestDto.getStarted_at();
        this.ended_at = requestDto.getEnded_at();
    }
}
