package jandi.server.model;

import jandi.server.util.RandomGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Event extends Timestamped{

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = RandomGenerator.generatorName)
    @GenericGenerator(name = RandomGenerator.generatorName, strategy = "jandi.server.util.RandomGenerator")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "started_at")
    private String started_at;

    @Column(name = "ended_at")
    private String ended_at;

    @Column(name = "content")
    private String content;

    // ??
    private List<Participant> participants;

    public Event(EventRequestDto requestDto) {
        this.name = requestDto.getName();
        this.content = requestDto.getContent();
        this.started_at = requestDto.getStarted_at();
        this.ended_at = requestDto.getEnded_at();

        for (int i=0; i<requestDto.getParticipants().size(); i++)
            participants.add(new Participant(this.id, requestDto.getParticipants().get(i)));
    }

    // 비효율적
    public void update(EventRequestDto requestDto) {
        this.name = requestDto.getName();
        this.content = requestDto.getContent();
        this.started_at = requestDto.getStarted_at();
        this.ended_at = requestDto.getEnded_at();

        for (int i=0; i<requestDto.getParticipants().size(); i++)
            participants.add(new Participant(this.id, requestDto.getParticipants().get(i)));
    }

}
