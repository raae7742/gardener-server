package jandi.server.service;

import jandi.server.model.*;
import jandi.server.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final UserService userService;

    public Long create(EventRequestDto requestDto) {
        Event event = eventRepository.save(new Event(requestDto));
        for (int i = 0; i<requestDto.getUsers().size(); i++) {
            userService.create(requestDto.getUsers().get(i), event);
        }
        return event.getId();
    }

    public Event findOne(Long id) {
        return eventRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
    }

    @Transactional
    public List<EventResponseDto> findCurrentEvents() {
        List<Event> currentEvents = eventRepository.findCurrentEvents();

        List<EventResponseDto> eventDtos = new ArrayList<>();
        for (int i=0; i<currentEvents.size(); i++) {
            eventDtos.add(new EventResponseDto(currentEvents.get(i)));
        }
        return eventDtos;
    }

    @Transactional
    public List<EventResponseDto> findPastEvents() {
        List<Event> pastEvents = eventRepository.findPastEvents();

        List<EventResponseDto> eventDtos = new ArrayList<>();
        for (int i=0; i<pastEvents.size(); i++) {
            eventDtos.add(new EventResponseDto(pastEvents.get(i)));
        }
        return eventDtos;
    }

    public Long delete(Long id) {
        Event event = findOne(id);
        userService.delete(event);
        eventRepository.delete(event);

        return event.getId();
    }
}
