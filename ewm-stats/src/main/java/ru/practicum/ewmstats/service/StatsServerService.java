package ru.practicum.ewmstats.service;


import ru.practicum.ewmstats.model.Dto.EndpointHitDto;
import ru.practicum.ewmstats.model.Dto.ViewStatsDto;

import java.util.List;

public interface StatsServerService {

    EndpointHitDto createEndpointHit(EndpointHitDto endpointHitDto);

    List<ViewStatsDto> getEndpointHits(String start,
                                       String end,
                                       List<String> uris,
                                       Boolean unique);
}
