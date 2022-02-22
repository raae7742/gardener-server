package jandi.server.service;

import jandi.server.model.event.Event;
import jandi.server.model.event.dto.EventRequestDto;
import jandi.server.model.event.dto.EventResponseDto;
import jandi.server.model.event.enums.EventExceptionType;
import jandi.server.model.member.Member;
import jandi.server.repository.EventRepository;
import jandi.server.util.response.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EventService {

    private final EventRepository eventRepository;
    private final MemberService memberService;
    private final AttendanceService attendanceService;

    public Long create(EventRequestDto requestDto) {
        Event event = eventRepository.save(new Event(requestDto));
        for (int i = 0; i<requestDto.getMembers().size(); i++) {
            Member member = memberService.create(requestDto.getMembers().get(i), event);
            for (LocalDate date = event.getStarted_at(); date.isBefore(LocalDate.now()); date = date.plusDays(1)){
                attendanceService.create(member, date);
            }
        }
        return event.getId();
    }

    public Event findOne(Long id) {
        return eventRepository.findById(id).orElseThrow(
                () -> new CustomException(EventExceptionType.NOT_FOUND)
        );
    }

    public List<EventResponseDto> findCurrentEvents() {
        List<Event> currentEvents = eventRepository.findCurrentEvents();

        List<EventResponseDto> eventDtos = new ArrayList<>();
        for (int i=0; i<currentEvents.size(); i++) {
            eventDtos.add(new EventResponseDto(currentEvents.get(i)));
        }
        return eventDtos;
    }

    public List<EventResponseDto> findPastEvents() {
        List<Event> pastEvents = eventRepository.findPastEvents();

        List<EventResponseDto> eventDtos = new ArrayList<>();
        for (int i=0; i<pastEvents.size(); i++) {
            eventDtos.add(new EventResponseDto(pastEvents.get(i)));
        }
        return eventDtos;
    }

    public List<EventResponseDto> findFutureEvents() {
        List<Event> futureEvents = eventRepository.findFutureEvents();

        List<EventResponseDto> eventDtos = new ArrayList<>();
        for (int i=0; i<futureEvents.size(); i++) {
            eventDtos.add(new EventResponseDto(futureEvents.get(i)));
        }
        return eventDtos;
    }

    public Event update(Long id, EventRequestDto requestDto) {
        Event event = findOne(id);
        event.update(requestDto);

        return event;
    }

    public Long delete(Long id) {
        Event event = findOne(id);
        eventRepository.delete(event);

        return event.getId();
    }
}
