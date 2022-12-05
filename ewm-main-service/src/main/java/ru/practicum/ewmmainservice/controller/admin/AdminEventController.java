package ru.practicum.ewmmainservice.controller.admin;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.model.Dto.event.AdminUpdateEventRequest;
import ru.practicum.ewmmainservice.model.Dto.event.EventFullDto;
import ru.practicum.ewmmainservice.service.admin.AdminEventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/events")
@Slf4j
public class AdminEventController {

    private final AdminEventService service;

    public AdminEventController(AdminEventService service) {
        this.service = service;
    }

    @GetMapping
    public List<EventFullDto> getAllEventsByAdmin(@RequestParam(required = false) List<Integer> users,
                                                  @RequestParam(required = false) List<String> states,
                                                  @RequestParam(required = false) List<Integer> categories,
                                                  @RequestParam(required = false, defaultValue = "") String rangeStart,
                                                  @RequestParam(required = false, defaultValue = "") String rangeEnd,
                                                  @RequestParam(required = false, defaultValue = "0")
                                                  @PositiveOrZero Integer from,
                                                  @RequestParam(required = false, defaultValue = "10")
                                                  @Positive Integer size) {

        log.info("Получен запрос от админа на получения списка событий");
        return service.getAllEventsAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable Integer eventId) {
        log.info("Получен запрос на опубликование события с айди = {} ", eventId);
        return service.publishEventAdmin(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable Integer eventId) {
        log.info("Получен запрос на отклонение админом события с айди = {}", eventId);
        return service.rejectEventAdmin(eventId);
    }

    @PutMapping("/{eventId}")
    public EventFullDto editEventByAdmin(@PathVariable Integer eventId,
                                         @RequestBody AdminUpdateEventRequest adminUpdateEventRequest) {
        log.info("Получен запрос на изменения события с айди= {} от админа", eventId);
        return service.patchEventAdmin(eventId, adminUpdateEventRequest);
    }
}
