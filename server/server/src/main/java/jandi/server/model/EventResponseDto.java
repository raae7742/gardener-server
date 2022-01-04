package jandi.server.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EventResponseDto {
    private String name;

    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Aisa/Seoul")
    private LocalDate started_at;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Aisa/Seoul")
    private LocalDate ended_at;

    private int user_size;

    public EventResponseDto(Event event) {
        this.name = event.getName();
        this.content = event.getContent();
        this.started_at = event.getStarted_at();
        this.ended_at = event.getEnded_at();
        this.user_size = event.getUsers().size();
    }
}
