package jandi.server.controller;

import jandi.server.model.Event;
import jandi.server.model.EventRequestDto;
import jandi.server.repository.EventRepository;
import jandi.server.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final EventRepository eventRepository;

    @PostMapping("/events")
    public String create(@RequestBody EventRequestDto requestDto) {
        return eventService.create(requestDto);
    }

    @PatchMapping("/events")
    public String update(@RequestPart String id, @RequestPart EventRequestDto requestDto) {
        return eventService.update(id, requestDto);
    }

    @GetMapping("/events")
    public List<Event> readCurrentEvents() {
        return eventRepository.findCurrentdEvents();
    }

    @GetMapping("/events/past")
    public List<Event> readPastEvents() {
        return eventRepository.findPastEvents();
    }

    @DeleteMapping("/events")
    public String delete(@RequestParam String id) {
        return eventService.delete(id);
    }
}
