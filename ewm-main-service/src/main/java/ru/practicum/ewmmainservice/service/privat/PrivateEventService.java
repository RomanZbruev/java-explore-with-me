package ru.practicum.ewmmainservice.service.privat;

import ru.practicum.ewmmainservice.model.Dto.event.EventFullDto;
import ru.practicum.ewmmainservice.model.Dto.event.EventShortDto;
import ru.practicum.ewmmainservice.model.Dto.event.NewEventDto;
import ru.practicum.ewmmainservice.model.Dto.event.UpdateEventRequest;
import ru.practicum.ewmmainservice.model.Dto.request.ParticipationRequestDto;

import java.util.List;

public interface PrivateEventService {

    List<EventShortDto> getEventsByUserId(Integer id, Integer from, Integer size);

    EventFullDto updateEventPrivate(Integer userId, UpdateEventRequest request);

    EventFullDto postEventPrivate(Integer userId, NewEventDto eventDto);

    EventFullDto getEventById(Integer eventId, Integer userId);

    EventFullDto cancelEventPrivate(Integer eventId, Integer userId);

    List<ParticipationRequestDto> getRequestUserInEventPrivate(Integer eventId, Integer userId);

    ParticipationRequestDto confirmRequestPrivate(Integer eventId, Integer userId, Integer reqId);

    ParticipationRequestDto rejectRequestPrivate(Integer eventId, Integer userId, Integer reqId);
}
