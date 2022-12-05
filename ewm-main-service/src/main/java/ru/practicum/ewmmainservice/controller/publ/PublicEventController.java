package ru.practicum.ewmmainservice.controller.publ;


import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.model.Dto.event.EventFullDto;
import ru.practicum.ewmmainservice.model.Dto.event.EventShortDto;
import ru.practicum.ewmmainservice.service.publ.PublicEventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/events")
@Validated
@Slf4j
public class PublicEventController {

    private final PublicEventService publicEventService;

    public PublicEventController(PublicEventService publicEventService) {
        this.publicEventService = publicEventService;
    }

    @GetMapping
    public List<EventShortDto> getEventListPublic(@RequestParam(defaultValue = "") String text,
                                           @RequestParam(required = false) List<Integer> categories,
                                           @RequestParam(required = false) Boolean paid,
                                           @RequestParam(defaultValue = "") String rangeStart,
                                           @RequestParam(defaultValue = "") String rangeEnd,
                                           @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                           @RequestParam(defaultValue = "") String sort,
                                           @RequestParam(required = false, defaultValue = "0")
                                           @PositiveOrZero Integer from,
                                           @RequestParam(required = false, defaultValue = "10")
                                           @Positive Integer size,
                                           HttpServletRequest request
    ) {
        log.info("Получен запрос на список событий с параметрами");
        return publicEventService.getEventListPublic(text, categories,
                paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, request);
    }

    @GetMapping(path = "/{id}")
    public EventFullDto getEventByIdPublic(@PathVariable Integer id, HttpServletRequest request) {
        log.info("Получен запрос на просмотр события с айди = {}", id);
        return publicEventService.getEventByIdPublic(id, request);
    }
}
