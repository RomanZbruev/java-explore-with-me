package ru.practicum.ewmmainservice.service.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.exception.BadRequestException;
import ru.practicum.ewmmainservice.exception.NotFoundException;
import ru.practicum.ewmmainservice.model.Category;
import ru.practicum.ewmmainservice.model.Dto.event.AdminUpdateEventRequest;
import ru.practicum.ewmmainservice.model.Dto.event.EventFullDto;
import ru.practicum.ewmmainservice.model.Event;
import ru.practicum.ewmmainservice.model.EventState;
import ru.practicum.ewmmainservice.model.User;
import ru.practicum.ewmmainservice.model.mapper.EventMapper;
import ru.practicum.ewmmainservice.repository.CategoryRepository;
import ru.practicum.ewmmainservice.repository.EventRepository;
import ru.practicum.ewmmainservice.repository.UserRepository;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminEventServiceImpl implements AdminEventService {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    public AdminEventServiceImpl(EventRepository eventRepository,
                                 CategoryRepository categoryRepository,
                                 UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public EventFullDto rejectEventAdmin(Integer eventId) {
        Event event = eventRepository.findEventById(eventId);
        if (event == null) {
            log.error("Событие с айди = {} не найдено", eventId);
            throw new NotFoundException("Событие с айди = " + eventId + " не найдено");
        }
        if (event.getState().equals(EventState.PUBLISHED)) {
            log.error("Событие уже опубликовано");
            throw new BadRequestException("Событие уже опубликовано");
        }
        event.setState(EventState.CANCELED);
        Event inMemory = eventRepository.save(event);
        log.info("Событие отклонено");
        return EventMapper.fromEventToFullDto(inMemory);
    }

    @Override
    public EventFullDto publishEventAdmin(Integer eventId) {
        Event event = eventRepository.findEventById(eventId);
        if (event == null) {
            log.error("Событие с айди = {} не найдено", eventId);
            throw new NotFoundException("Событие с айди = " + eventId + " не найдено");
        }
        if (event.getEventDate().minusHours(1).isBefore(LocalDateTime.now())) {
            log.error("До события осталось меньше часа");
            throw new BadRequestException("До события осталось меньше часа, ошибка опубликования");
        }
        if (!event.getState().equals(EventState.PENDING)) {
            log.error("Событие не находится в состоянии ожидания");
            throw new BadRequestException("Событие не находится в состоянии ожидания, ошибка подтверждения");
        }
        event.setState(EventState.PUBLISHED);
        event.setPublishedOn(LocalDateTime.now());
        Event inMemory = eventRepository.save(event);
        log.info("Событие опубликовано");
        return EventMapper.fromEventToFullDto(inMemory);
    }

    @Override
    public List<EventFullDto> getAllEventsAdmin(List<Integer> users,
                                                List<String> states,
                                                List<Integer> categories,
                                                String rangeStart,
                                                String rangeEnd,
                                                Integer from,
                                                Integer size) {
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
        if (users == null) {
            users = List.of();
        }
        if (categories == null) {
            categories = List.of();
        }
        List<User> userList = userRepository.getUsersByIds(users);
        List<Category> categoryList = categoryRepository.findCategoriesByIds(categories);
        List<EventState> eventStateList = new ArrayList<>();
        if (states != null) {
            for (String state : states) {
                if (EventState.PENDING.equals(EventState.valueOf(state))) {
                    eventStateList.add(EventState.PENDING);
                } else if (EventState.CANCELED.equals(EventState.valueOf(state))) {
                    eventStateList.add(EventState.CANCELED);
                } else if (EventState.PUBLISHED.equals(EventState.valueOf(state))) {
                    eventStateList.add(EventState.PUBLISHED);
                } else {
                    log.error("Статуса события с названием = " + state + " не существует");
                    throw new BadRequestException("Статуса события с названием = " + state + " не существует");
                }
            }
        } else {
            eventStateList.addAll(List.of(EventState.values()));
        }

        Page<Event> eventPage = eventRepository.getEventsAdmin(userList, categoryList,
                eventStateList, rangeStartFormatted, rangeEndFormatted, pageable);
        return eventPage.stream()
                .map(EventMapper::fromEventToFullDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto patchEventAdmin(Integer eventId, AdminUpdateEventRequest request) {
        if (request == null) {
            throw new BadRequestException("Передан пустой объект для обновления события");
        }
        Event event = eventRepository.findEventById(eventId);
        if (event == null) {
            log.error("Событие с айди = {} не найдено", eventId);
            throw new NotFoundException("Событие с айди = " + eventId + " не найдено");
        }

        Event prepareOnSave = updateParamEventAdmin(event, request);

        Event inMemory = eventRepository.save(prepareOnSave);

        log.info("Событие с айди = {} обновлено", eventId);

        return EventMapper.fromEventToFullDto(inMemory);

    }

    private Event updateParamEventAdmin(Event event, AdminUpdateEventRequest request) {

        if (request.getAnnotation() != null) {
            event.setAnnotation(request.getAnnotation());
        }
        if (request.getCategory() != null) {
            Category category = categoryRepository.findCategoryById(request.getCategory());
            if (category == null) {
                log.error("Категория с айди = {} не найден", request.getCategory());
                throw new NotFoundException("Категория с айди = " + request.getCategory() + " не найдена");
            }
            event.setCategory(category);
        }
        if (request.getDescription() != null) {
            event.setDescription(request.getDescription());
        }
        if (request.getEventDate() != null) {
            event.setEventDate(request.getEventDate());
        }
        if (request.getLocation() != null) {
            event.setLat(request.getLocation().getLat());
            event.setLon(request.getLocation().getLon());
        }
        if (request.getPaid() != null) {
            event.setPaid(request.getPaid());
        }
        if (request.getParticipantLimit() != null) {
            event.setParticipantLimit(request.getParticipantLimit());
        }
        if (request.getRequestModeration() != null) {
            event.setRequestModeration(request.getRequestModeration());
        }
        if (request.getTitle() != null) {
            event.setTitle(request.getTitle());
        }
        return event;
    }
}
