package ru.practicum.ewmstats.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmstats.model.Dto.EndpointHitDto;
import ru.practicum.ewmstats.model.Dto.ViewStatsDto;
import ru.practicum.ewmstats.service.StatsServerService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "")
public class StatsServerController {

    private final StatsServerService service;

    public StatsServerController(StatsServerService service) {
        this.service = service;
    }

    @PostMapping("/hit")
    public EndpointHitDto createEndpointHit(@RequestBody EndpointHitDto endpointHitDto) {
        log.info("Получен запрос на создание строки хранения статистики для эндпоинта");
        return service.createEndpointHit(endpointHitDto);
    }

    @GetMapping("/stats")
    public ResponseEntity<Object> getEndpointHits(@RequestParam String start,
                                                  @RequestParam String end,
                                                  @RequestParam List<String> uris,
                                                  @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("Получение статистики по посещениям с параметрами start = {}, end = {}, uris = {}, unique = {} ",
                start, end, uris, unique);
        List<ViewStatsDto> viewStatsDtos = service.getEndpointHits(start, end, uris, unique);
        return new ResponseEntity<>(viewStatsDtos, HttpStatus.OK);
    }
}
