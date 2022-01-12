package jandi.server.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EventResponseDto {
    private Long id;

    private String name;

    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Aisa/Seoul")
    private LocalDate started_at;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Aisa/Seoul")
    private LocalDate ended_at;

    private List<UserResponseDto> users = new ArrayList<>();

    public EventResponseDto(Event event) {
        this.id = event.getId();
        this.name = event.getName();
        this.content = event.getContent();
        this.started_at = event.getStarted_at();
        this.ended_at = event.getEnded_at();
        for (User user : event.getUsers())
            users.add(new UserResponseDto(user));
    }
}



