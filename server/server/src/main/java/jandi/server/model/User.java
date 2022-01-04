package jandi.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User extends Timestamped {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "name")
    private String name;

    @Column(name = "github")
    private String github;

    @OneToMany(mappedBy = "user")
    private List<Attendance> attendances = new ArrayList<>();

    public void setEvent(Event event) {
        this.event = event;
        event.getUsers().add(this);
    }

    public User(UserRequestDto requestDto, Event event) {
        this.name = requestDto.getName();
        this.github = requestDto.getGithub();
        setEvent(event);
    }
}
