package ru.practicum.ewmmainservice.model.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewmmainservice.model.Dto.event.EventFullDto;
import ru.practicum.ewmmainservice.model.Dto.event.NewEventDto;
import ru.practicum.ewmmainservice.model.Dto.event.EventShortDto;
import ru.practicum.ewmmainservice.model.Event;
import ru.practicum.ewmmainservice.model.Location;

import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class EventMapper {

    public static final DateTimeFormatter TIME_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Set<EventShortDto> fromEventToShortDto(Set<Event> events) {
        return events.stream()
                .map(EventMapper::fromEventToShortDto)
                .collect(Collectors.toSet());
    }

    public static Event fromNewEventDto(NewEventDto newEventDto) {
        return Event.builder()
                .annotation(newEventDto.getAnnotation())
                .description(newEventDto.getDescription())
                .lat(newEventDto.getLocation().getLat())
                .lon(newEventDto.getLocation().getLon())
                .participantLimit(newEventDto.getParticipantLimit())
                .eventDate(newEventDto.getEventDate())
                .paid(newEventDto.getPaid())
                .title(newEventDto.getTitle())
                .requestModeration(newEventDto.getRequestModeration())
                .build();
    }

    public static EventFullDto fromEventToFullDto(Event event) {
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.fromCategory(event.getCategory()))
                .description(event.getDescription())
                .location(new Location(event.getLat(), event.getLon()))
                .participantLimit(event.getParticipantLimit())
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedOn())
                .publishedOn(event.getPublishedOn())
                .eventDate(event.getEventDate().format(TIME_PATTERN))
                .initiator(UserMapper.fromUserShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .build();
    }

    public static EventShortDto fromEventToShortDto(Event event) {
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.fromCategory(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate().format(TIME_PATTERN))
                .initiator(UserMapper.fromUserShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .build();
    }
}
