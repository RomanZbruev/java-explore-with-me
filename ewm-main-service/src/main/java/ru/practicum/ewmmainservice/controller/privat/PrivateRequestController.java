package ru.practicum.ewmmainservice.controller.privat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.model.Dto.request.ParticipationRequestDto;
import ru.practicum.ewmmainservice.service.privat.PrivateRequestService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/users/{userId}/requests")
public class PrivateRequestController {

    private final PrivateRequestService privateRequestService;

    public PrivateRequestController(PrivateRequestService privateRequestService) {
        this.privateRequestService = privateRequestService;
    }

    @GetMapping
    List<ParticipationRequestDto> getUserRequestsPrivate(@PathVariable Integer userId) {
        log.info("Получен запрос на просмотр заявок от пользователя с айди = {}", userId);
        return privateRequestService.getUserRequestsPrivate(userId);
    }

    @PostMapping
    ParticipationRequestDto postRequestPrivate(@PathVariable Integer userId,
                                               @RequestParam Integer eventId) {
        log.info("Получен запрос на создание заявки от пользователя с айди = {}", userId);
        return privateRequestService.postRequestPrivate(userId, eventId);
    }

    @PatchMapping(path = "/{requestId}/cancel")
    ParticipationRequestDto cancelRequest(@PathVariable Integer requestId, @PathVariable Integer userId) {
        log.info("Получен запрос на отмену заявки с айди = {} от пользователя с айди = {}", requestId, userId);
        return privateRequestService.cancelRequest(requestId, userId);
    }
}
