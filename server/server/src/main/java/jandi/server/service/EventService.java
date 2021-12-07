package jandi.server.service;

import jandi.server.model.Event;
import jandi.server.model.EventRequestDto;
import jandi.server.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    // 이벤트 생성
    public String create (EventRequestDto requestDto) {
        Event event = eventRepository.save(new Event(requestDto));

        return event.getId();
    }

    // 이벤트 수정
    public String update(String id, EventRequestDto requestDto) {
        Event event = eventRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        event.update(requestDto);

        return event.getId();
    }

    // 이벤트 삭제
    public String delete(String id) {
        Event event = eventRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        eventRepository.delete(event);

        return event.getId();
    }
}
