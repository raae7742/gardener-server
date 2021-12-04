package jandi.server.model;

import jandi.server.util.RandomGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Participant extends Timestamped {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = RandomGenerator.generatorName)
    @GenericGenerator(name = RandomGenerator.generatorName, strategy = "jandi.server.util.RandomGenerator")
    private String id;

    @ManyToOne
    @Column(name = "event_id")
    private String event_id;

    @Column(name = "name")
    private String name;

    @Column(name = "github")
    private String github;

    public Participant(String event_id, ParticipantRequestDto requestDto) {
        this.event_id = event_id;
        this.name = requestDto.getName();
        this.github = requestDto.getGithub();
    }
}
