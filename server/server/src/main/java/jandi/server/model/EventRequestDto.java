package jandi.server.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class EventRequestDto {
    private String name;

    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate started_at;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate ended_at;

    private List<UserRequestDto> users;
}
