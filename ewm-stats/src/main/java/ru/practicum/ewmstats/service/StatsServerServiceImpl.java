package ru.practicum.ewmstats.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmstats.model.Dto.EndpointHitDto;
import ru.practicum.ewmstats.model.Dto.ViewStatsDto;
import ru.practicum.ewmstats.model.EndpointHit;
import ru.practicum.ewmstats.model.mapper.EndpointHitMapper;
import ru.practicum.ewmstats.repository.StatsRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class StatsServerServiceImpl implements StatsServerService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final StatsRepository statsRepository;

    public StatsServerServiceImpl(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    @Transactional
    @Override
    public EndpointHitDto createEndpointHit(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = statsRepository.save(EndpointHitMapper.toEndpointHit(endpointHitDto));
        log.info("Эндпоинт с айди = {} сохранен", endpointHit.getId());
        return EndpointHitMapper.toEndpointHitDto(endpointHit);
    }

    @Override
    public List<ViewStatsDto> getEndpointHits(String start, String end, List<String> uris, Boolean unique) {

        LocalDateTime startTimeFormatted = LocalDateTime.parse(start, FORMATTER);
        LocalDateTime endTimeFormatted = LocalDateTime.parse(end, FORMATTER);

        List<Object[]> endpointHits;
        if (unique.equals(true)) {
            endpointHits = statsRepository.getEndpointHitsUnique(startTimeFormatted, endTimeFormatted, uris);
        } else {
            endpointHits = statsRepository.getEndpointHitsNotUnique(startTimeFormatted, endTimeFormatted, uris);
        }

        List<ViewStatsDto> viewStatsDtos = new ArrayList<>();
        if (!endpointHits.isEmpty()) {
            for (Object[] object : endpointHits) {
                ViewStatsDto viewStatsDto = ViewStatsDto
                        .builder()
                        .app(object[0].toString())
                        .uri(object[1].toString())
                        .hits(Long.valueOf(object[2].toString()))
                        .build();
                viewStatsDtos.add(viewStatsDto);
            }
        }
        return viewStatsDtos;
    }
}

