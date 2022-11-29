package ru.practicum.ewmmainservice.service.publ;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.client.EventClient;
import ru.practicum.ewmmainservice.exception.BadRequestException;
import ru.practicum.ewmmainservice.exception.NotFoundException;
import ru.practicum.ewmmainservice.model.Category;
import ru.practicum.ewmmainservice.model.Dto.event.EventFullDto;
import ru.practicum.ewmmainservice.model.Dto.event.EventShortDto;
import ru.practicum.ewmmainservice.model.Dto.stats.EndpointHitDtoEMW;
import ru.practicum.ewmmainservice.model.Event;
import ru.practicum.ewmmainservice.model.EventState;
import ru.practicum.ewmmainservice.model.mapper.EventMapper;
import ru.practicum.ewmmainservice.repository.CategoryRepository;
import ru.practicum.ewmmainservice.repository.EventRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PublicEventServiceImpl implements PublicEventService {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final EventRepository eventRepository;

    private final CategoryRepository categoryRepository;
    private final EventClient eventClient;

    public PublicEventServiceImpl(EventRepository eventRepository, CategoryRepository categoryRepository,
                                  EventClient eventClient) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.eventClient = eventClient;
    }

    @Override
    public EventFullDto getEventByIdPublic(Integer id, HttpServletRequest request) {
        eventClient.createEndpointHit(EndpointHitDtoEMW.builder()
                .app("ewm-main-service")
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now().format(FORMATTER))
                .build());
        Event event = eventRepository.findEventById(id);
        if (event == null) {
            log.error("Событие с айди = {} не найдено", id);
            throw new NotFoundException("Событие с айди = " + id + " не найдено");
        }
        if (!event.getState().equals(EventState.PUBLISHED)) {
            log.error("Событие с айди = {} не опубликовано", id);
            throw new BadRequestException("Событие с айди = " + id + " не опубликовано");
        }

        String uri = request.getRequestURI();
        ArrayList<LinkedHashMap<Object, Object>> stats = findViews(uri);
        LinkedHashMap<Object, Object> statsHits = stats.get(0);
        Integer views = Integer.parseInt(String.valueOf(statsHits.get("hits")));
        EventFullDto eventFullDto = EventMapper.fromEventToFullDto(event);
        eventFullDto.setViews(views);
        log.info("Для события с айди = {} получена статистика просмотров", id);

        return eventFullDto;
    }

    @Override
    public List<EventShortDto> getEventListPublic(String text,
                                                  List<Integer> categories,
                                                  Boolean paid,
                                                  String rangeStart,
                                                  String rangeEnd,
                                                  Boolean onlyAvailable,
                                                  String sort,
                                                  Integer from,
                                                  Integer size,
                                                  HttpServletRequest request) {

        eventClient.createEndpointHit(EndpointHitDtoEMW.builder()
                .app("ewm-main-service")
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now().format(FORMATTER))
                .build());

        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);

        LocalDateTime rangeStartFormatted;
        LocalDateTime rangeEndFormatted;
        if (rangeStart.isBlank()) {
            rangeStartFormatted = LocalDateTime.now();
        } else {
            rangeStartFormatted = LocalDateTime.parse(rangeStart, FORMATTER);
        }
        if (rangeEnd.isBlank()) {
            rangeEndFormatted = LocalDateTime.now().plusYears(100);
        } else {
            rangeEndFormatted = LocalDateTime.parse(rangeEnd, FORMATTER);
        }

        List<Category> categoriesList = categoryRepository.findCategoriesByIds(categories);

        Page<Event> eventPage = null;

        if (text.isBlank()) {
            if (onlyAvailable.equals(true)) {
                if (sort.equals("EVENT_DATE") || sort.isBlank()) {
                    eventPage = eventRepository.getAllEventsByEventDateAvailablePublic(categoriesList,
                            paid, rangeStartFormatted, rangeEndFormatted, pageable);
                }
                if (sort.equals("VIEWS")) {
                    eventPage = eventRepository.getAllEventsByViewsAvailablePublic(categoriesList, paid,
                            rangeStartFormatted, rangeEndFormatted, pageable);
                }
            }
        } else {
            if (onlyAvailable.equals(true)) {
                if (sort.equals("EVENT_DATE") || sort.isBlank()) {
                    eventPage = eventRepository.getAllEventsByEventDateAvailableWithTextPublic(text, categoriesList,
                            paid, rangeStartFormatted, rangeEndFormatted, pageable);
                }
                if (sort.equals("VIEWS")) {
                    eventPage = eventRepository.getAllEventsByViewsAvailableWithTextPublic(text, categoriesList, paid,
                            rangeStartFormatted, rangeEndFormatted, pageable);
                }
            }
        }

        if (text.isBlank()) {
            if (sort.equals("EVENT_DATE") || sort.isBlank()) {
                eventPage = eventRepository.getAllEventsByEventDatePublic(categoriesList,
                        paid, rangeStartFormatted, rangeEndFormatted, pageable);
            }
            if (sort.equals("VIEWS")) {
                eventPage = eventRepository.getAllEventsByViewsPublic(categoriesList, paid,
                        rangeStartFormatted, rangeEndFormatted, pageable);
            }
        } else {
            if (sort.equals("EVENT_DATE") || sort.isBlank()) {
                eventPage = eventRepository.getAllEventsByEventDateWithTextPublic(text, categoriesList,
                        paid, rangeStartFormatted, rangeEndFormatted, pageable);
            }
            if (sort.equals("VIEWS")) {
                eventPage = eventRepository.getAllEventsByViewsWithTextPublic(text, categoriesList, paid,
                        rangeStartFormatted, rangeEndFormatted, pageable);
            }
        }
        List<EventShortDto> eventShortDtos = new ArrayList<>();
        if (eventPage != null) {
            eventShortDtos = eventPage.getContent().stream()
                    .map(EventMapper::fromEventToShortDto)
                    .collect(Collectors.toList());
        }

        List<String> uris = new ArrayList<>();
        for (EventShortDto eventShortDto : eventShortDtos) {
            String uri = "/events/" + eventShortDto.getId();
            uris.add(uri);
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < uris.size(); i++) {
            if (i < (uris.size()) - 1) {
                builder.append(uris.get(i)).append("&");
            } else {
                builder.append(uris.get(i));
            }
        }
        String fullUri = builder.toString();

        ArrayList<LinkedHashMap<Object, Object>> stats = findViews(fullUri);
        if (!stats.isEmpty()) {
            HashMap<Integer, Integer> eventHits = new HashMap<>();
            for (LinkedHashMap<Object, Object> mapFromStatsOfEventHits : stats) {
                String uriForSplit = String.valueOf(mapFromStatsOfEventHits.get("uri"));
                String[] splitOfUri = uriForSplit.split("/");
                Integer eventId = Integer.parseInt(splitOfUri[2]);
                Integer hits = Integer.parseInt(String.valueOf(mapFromStatsOfEventHits.get("hits")));

                eventHits.put(eventId, hits);
            }
            for (EventShortDto event : eventShortDtos) {
                Set<Integer> keys = eventHits.keySet();
                if (keys.contains(event.getId())) {
                    Integer views = eventHits.get(event.getId());
                    event.setViews(views);
                    log.info("Для события с айди = {} сохранены просмотры", event.getId());
                }
            }
        } else {
            for (EventShortDto event : eventShortDtos) {
                event.setViews(0);
                log.info("Для события с айди = {} без просмотров установлено значение 0", event.getId());
            }
        }

        return eventShortDtos;
    }

    private ArrayList<LinkedHashMap<Object, Object>> findViews(String uri) {
        String start = LocalDateTime.now().minusYears(100).format(FORMATTER);
        String end = LocalDateTime.now().plusYears(100).format(FORMATTER);
        ResponseEntity<Object> responseEntities = eventClient.getEndpointHits(start, end, uri, false);
        ArrayList<LinkedHashMap<Object, Object>> listFromObject = new ArrayList<>();
        if (responseEntities != null) {
            listFromObject = (ArrayList<LinkedHashMap<Object, Object>>) responseEntities.getBody();
        }
        return listFromObject;
    }
}
