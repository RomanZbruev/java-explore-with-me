package ru.practicum.ewmmainservice.service.admin;

import ru.practicum.ewmmainservice.model.Dto.event.AdminUpdateEventRequest;
import ru.practicum.ewmmainservice.model.Dto.event.EventFullDto;

import java.util.List;

public interface AdminEventService {

    EventFullDto rejectEventAdmin(Integer eventId);

    EventFullDto publishEventAdmin(Integer eventId);

    List<EventFullDto> getAllEventsAdmin(List<Integer> users,
                                         List<String> states,
                                         List<Integer> categories,
                                         String rangeStart,
                                         String rangeEnd,
                                         Integer from,
                                         Integer size);


    EventFullDto patchEventAdmin(Integer eventId, AdminUpdateEventRequest request);
}
