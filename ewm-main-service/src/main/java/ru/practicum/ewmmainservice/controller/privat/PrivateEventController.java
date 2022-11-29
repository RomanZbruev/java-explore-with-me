package ru.practicum.ewmmainservice.controller.privat;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.model.Dto.event.EventFullDto;
import ru.practicum.ewmmainservice.model.Dto.event.EventShortDto;
import ru.practicum.ewmmainservice.model.Dto.event.NewEventDto;
import ru.practicum.ewmmainservice.model.Dto.event.UpdateEventRequest;
import ru.practicum.ewmmainservice.model.Dto.request.ParticipationRequestDto;
import ru.practicum.ewmmainservice.service.privat.PrivateEventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/users/{userId}/events")
public class PrivateEventController {

    private final PrivateEventService privateEventService;

    public PrivateEventController(PrivateEventService privateEventService) {
        this.privateEventService = privateEventService;
    }

    @GetMapping
    public List<EventShortDto> getEventsByUserIdPrivate(@PathVariable Integer userId,
                                                        @RequestParam(required = false, defaultValue = "0")
                                                        @PositiveOrZero Integer from,
                                                        @RequestParam(required = false, defaultValue = "10")
                                                        @Positive Integer size) {
        log.info("Получен запрос на просмотр списка событий, созданных пользователем с айди = {}", userId);
        return privateEventService.getEventsByUserId(userId, from, size);
    }

    @PatchMapping
    public EventFullDto updateUserEventsPrivate(@PathVariable Integer userId, @RequestBody UpdateEventRequest request) {
        log.info("Получен запрос на изменения события для пользователя с айди = {}", userId);
        return privateEventService.updateEventPrivate(userId, request);
    }

    @PostMapping
    public EventFullDto postEventPrivate(@PathVariable Integer userId, @RequestBody @Valid NewEventDto dto) {
        log.info("Получен запрос на изменения события для пользователя с айди = {}", userId);
        return privateEventService.postEventPrivate(userId, dto);
    }

    @GetMapping(path = "/{eventId}")
    public EventFullDto getEventUserByIdPrivate(@PathVariable Integer eventId, @PathVariable Integer userId) {
        log.info("Получен запрос на просмотр события с айди = {}, пользователя с айди = {}", eventId, userId);
        return privateEventService.getEventById(eventId, userId);
    }

    @PatchMapping(path = "/{eventId}")
    public EventFullDto cancelEventPrivate(@PathVariable Integer eventId, @PathVariable Integer userId) {
        log.info("Получен запрос на отмену события с айди = {}, пользователя с айди = {}", eventId, userId);
        return privateEventService.cancelEventPrivate(eventId, userId);
    }


    @GetMapping(path = "/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestUserInEventPrivate(@PathVariable Integer eventId,
                                                                      @PathVariable Integer userId) {
        log.info("Получен запрос на просмотр заявой для события с айди = {}, от пользователя с айди = {}", eventId, userId);
        return privateEventService.getRequestUserInEventPrivate(eventId, userId);
    }

    @PatchMapping(path = "/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequestPrivate(@PathVariable Integer eventId,
                                                         @PathVariable Integer userId,
                                                         @PathVariable Integer reqId) {
        log.info("Получен запрос на подтверждение заявки на событие с айди = {}, от пользователя с айди = {}",
                eventId, userId);
        return privateEventService.confirmRequestPrivate(eventId, userId, reqId);
    }

    @PatchMapping(path = "/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequestPrivate(@PathVariable Integer eventId,
                                                        @PathVariable Integer userId,
                                                        @PathVariable Integer reqId) {
        log.info("Получен запрос на отклонение заявки на событие с айди = {}, от пользователя с айди = {}",
                eventId, userId);
        return privateEventService.rejectRequestPrivate(eventId, userId, reqId);
    }

}
