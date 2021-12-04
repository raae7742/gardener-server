package jandi.server.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EventRequestDto {
    private String name;
    private String content;
    private String started_at;
    private String ended_at;
    private List<ParticipantRequestDto> participants;
}
