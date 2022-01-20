package jandi.server.service;

import jandi.server.model.*;
import jandi.server.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final UserService userService;
    private final AttendanceService attendanceService;

    public Long create(EventRequestDto requestDto) {
        Event event = eventRepository.save(new Event(requestDto));
        for (int i = 0; i<requestDto.getUsers().size(); i++) {
            User user = userService.create(requestDto.getUsers().get(i), event);
            for (LocalDate date = event.getStarted_at(); date.isBefore(LocalDate.now()); date = date.plusDays(1))
                attendanceService.create(user, date);
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

    @Transactional
    public List<EventResponseDto> findFutureEvents() {
        List<Event> futureEvents = eventRepository.findFutureEvents();

        List<EventResponseDto> eventDtos = new ArrayList<>();
        for (int i=0; i<futureEvents.size(); i++) {
            eventDtos.add(new EventResponseDto(futureEvents.get(i)));
        }
        return eventDtos;
    }

    public Long delete(Long id) {
        Event event = findOne(id);
        eventRepository.delete(event);

        return event.getId();
    }
}
