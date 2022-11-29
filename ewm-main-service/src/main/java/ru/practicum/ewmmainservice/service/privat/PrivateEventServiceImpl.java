package ru.practicum.ewmmainservice.service.privat;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.exception.BadRequestException;
import ru.practicum.ewmmainservice.exception.ForbiddenException;
import ru.practicum.ewmmainservice.exception.NotFoundException;
import ru.practicum.ewmmainservice.model.*;
import ru.practicum.ewmmainservice.model.Dto.event.EventFullDto;
import ru.practicum.ewmmainservice.model.Dto.event.EventShortDto;
import ru.practicum.ewmmainservice.model.Dto.event.NewEventDto;
import ru.practicum.ewmmainservice.model.Dto.event.UpdateEventRequest;
import ru.practicum.ewmmainservice.model.Dto.request.ParticipationRequestDto;
import ru.practicum.ewmmainservice.model.mapper.EventMapper;
import ru.practicum.ewmmainservice.model.mapper.RequestMapper;
import ru.practicum.ewmmainservice.repository.CategoryRepository;
import ru.practicum.ewmmainservice.repository.EventRepository;
import ru.practicum.ewmmainservice.repository.RequestRepository;
import ru.practicum.ewmmainservice.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PrivateEventServiceImpl implements PrivateEventService {

    private final UserRepository userRepository;

    private final EventRepository eventRepository;

    private final CategoryRepository categoryRepository;

    private final RequestRepository requestRepository;

    public PrivateEventServiceImpl(UserRepository userRepository, EventRepository eventRepository,
                                   CategoryRepository categoryRepository, RequestRepository requestRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.requestRepository = requestRepository;
    }


    @Override
    public List<EventShortDto> getEventsByUserId(Integer id, Integer from, Integer size) {
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);
        User user = userRepository.getUserById(id);
        if (user == null) {
            log.error("Пользователь с айди = {} не найден", id);
            throw new NotFoundException("Пользователь с айди = " + id + " не найден");
        }
        List<Event> events = eventRepository.findEventsByInitiatorId(id, pageable);
        return events.stream()
                .map(EventMapper::fromEventToShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto updateEventPrivate(Integer userId, UpdateEventRequest request) {
        if (request == null) {
            log.error("Прислан пустой объект");
            throw new BadRequestException("Прислан пустой объект");
        }
        User user = userRepository.getUserById(userId);
        Event event = eventRepository.findEventById(request.getEventId());
        if (user == null) {
            log.error("Пользователь с айди = {} не найден", userId);
            throw new NotFoundException("Пользователь с айди = " + userId + " не найден");
        }
        if (event == null) {
            log.error("Событие с айди = {} не найдено", request.getEventId());
            throw new NotFoundException("Событие с айди = " + request.getEventId() + " не найдено");
        }
        if (!event.getInitiator().getId().equals(userId)) {
            log.error("Попытка изменения события не его создателем");
            throw new ForbiddenException("Попытка изменения события не его создателем");
        }
        if (!(event.getState().equals(EventState.CANCELED) || event.getState().equals(EventState.PENDING))) {
            log.error("Событие должно быть отмененным или в состоянии модерации");
            throw new BadRequestException("Событие должно быть отменненным или в состоянии модерации");
        }
        if (!(event.getEventDate().isAfter(LocalDateTime.now().minus(2, ChronoUnit.HOURS)))) {
            log.error("До начала события меньше двух часов");
            throw new BadRequestException("До начала события меньше двух часов");
        }
        Event eventInMemory = eventRepository.save(updateEventByUserUtil(event, request));
        log.info("Событие с айди {} сохранено", event.getId());
        return EventMapper.fromEventToFullDto(eventInMemory);
    }

    private Event updateEventByUserUtil(Event event, UpdateEventRequest updateEventRequest) {
        if (updateEventRequest.getAnnotation() != null) {
            event.setAnnotation(updateEventRequest.getAnnotation());
        }
        if (updateEventRequest.getCategory() != null) {
            Category category = categoryRepository.findCategoryById(updateEventRequest.getCategory());
            if (category == null) {
                throw new NotFoundException("Категория не найдена");
            }
            event.setCategory(category);
        }
        if (updateEventRequest.getDescription() != null) {
            event.setDescription(updateEventRequest.getDescription());
        }
        if (updateEventRequest.getEventDate() != null) {
            event.setEventDate(updateEventRequest.getEventDate());
        }
        if (updateEventRequest.getPaid() != null) {
            event.setPaid(updateEventRequest.getPaid());
        }
        if (updateEventRequest.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventRequest.getParticipantLimit());
        }
        if (updateEventRequest.getTitle() != null) {
            event.setTitle(updateEventRequest.getTitle());
        }
        event.setState(EventState.PENDING);
        return event;
    }

    @Override
    public EventFullDto postEventPrivate(Integer userId, NewEventDto eventDto) {
        User user = userRepository.getUserById(userId);
        Category category = categoryRepository.findCategoryById(eventDto.getCategory());
        if (user == null) {
            log.error("Пользователь с айди = {} не найден", userId);
            throw new NotFoundException("Пользователь с айди = " + userId + " не найден");
        }
        if (category == null) {
            log.error("Категория с айди = {} не найдена", eventDto.getCategory());
            throw new NotFoundException("Категория с айди = " + eventDto.getCategory() + " не найдена");
        }
        if (!(eventDto.getEventDate().isAfter(LocalDateTime.now().minusHours(2)))) {
            log.error("До начала события меньше двух часов");
            throw new BadRequestException("До начала события меньше двух часов");
        }

        Event event = EventMapper.fromNewEventDto(eventDto);
        event.setCategory(category);
        event.setInitiator(user);
        event.setState(EventState.PENDING);
        event.setCreatedOn(LocalDateTime.now());


        Event inMemory = eventRepository.save(event);
        return EventMapper.fromEventToFullDto(inMemory);
    }

    @Override
    public EventFullDto getEventById(Integer eventId, Integer userId) {
        Event validated = validateUserEventAndAccessRights(eventId, userId);
        return EventMapper.fromEventToFullDto(validated);
    }

    @Override
    public EventFullDto cancelEventPrivate(Integer eventId, Integer userId) {
        Event validated = validateUserEventAndAccessRights(eventId, userId);
        if (!validated.getInitiator().getId().equals(userId)) {
            log.error("Попытка изменения события не его создателем");
            throw new ForbiddenException("Попытка изменения события не его создателем");
        }
        if (!validated.getState().equals(EventState.PENDING)) {
            log.error("Попытка отменить событие не в состоянии модерации");
            throw new BadRequestException("Попытка отменить событие не в состоянии модерации");
        }
        validated.setState(EventState.CANCELED);
        Event saveInMemory = eventRepository.save(validated);
        return EventMapper.fromEventToFullDto(saveInMemory);
    }

    @Override
    public List<ParticipationRequestDto> getRequestUserInEventPrivate(Integer eventId, Integer userId) {
        validateUserEventAndAccessRights(eventId, userId);
        List<Request> requests = requestRepository.getRequestsByRequesterAndEvent(userId, eventId);
        return requests.stream()
                .map(RequestMapper::fromRequestToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto confirmRequestPrivate(Integer eventId, Integer userId, Integer reqId) {
        Event event = validateUserEventAndAccessRights(eventId, userId);
        Request request = requestRepository.getRequestById(reqId);
        if (request == null) {
            log.error("Запрос с айди = {} не найден", reqId);
            throw new NotFoundException("Запрос с айди = " + reqId + " не найден");
        }
        if (event.getParticipantLimit().equals(event.getConfirmedRequests())) {
            request.setStatus(RequestStatus.REJECTED);
        } else {
            request.setStatus(RequestStatus.CONFIRMED);
            Integer confirmed = event.getConfirmedRequests();
            if (confirmed == null) {
                confirmed = 0;
            }
            event.setConfirmedRequests(confirmed + 1);
            eventRepository.save(event);
        }
        Request inMemory = requestRepository.save(request);
        return RequestMapper.fromRequestToDto(inMemory);
    }

    @Override
    public ParticipationRequestDto rejectRequestPrivate(Integer eventId, Integer userId, Integer reqId) {
        Event event = validateUserEventAndAccessRights(eventId, userId);
        Request request = requestRepository.getRequestById(reqId);
        if (request == null) {
            log.error("Запрос с айди = {} не найден", reqId);
            throw new NotFoundException("Запрос с айди = " + reqId + " не найден");
        }
        request.setStatus(RequestStatus.REJECTED);
        Request inMemory = requestRepository.save(request);
        return RequestMapper.fromRequestToDto(inMemory);
    }

    private Event validateUserEventAndAccessRights(Integer eventId, Integer userId) {
        User user = userRepository.getUserById(userId);
        Event event = eventRepository.findEventById(eventId);
        if (user == null) {
            log.error("Пользователь с айди = {} не найден", userId);
            throw new NotFoundException("Пользователь с айди = " + userId + " не найден");
        }
        if (event == null) {
            log.error("Событие с айди = {} не найдено", eventId);
            throw new NotFoundException("Событие с айди = " + eventId + " не найдено");
        }
        return event;
    }
}
