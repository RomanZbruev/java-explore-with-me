package ru.practicum.ewmmainservice.service.privat;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmmainservice.exception.NotFoundException;
import ru.practicum.ewmmainservice.model.Dto.request.ParticipationRequestDto;
import ru.practicum.ewmmainservice.model.Event;
import ru.practicum.ewmmainservice.model.Request;
import ru.practicum.ewmmainservice.model.RequestStatus;
import ru.practicum.ewmmainservice.model.User;
import ru.practicum.ewmmainservice.model.mapper.RequestMapper;
import ru.practicum.ewmmainservice.repository.EventRepository;
import ru.practicum.ewmmainservice.repository.RequestRepository;
import ru.practicum.ewmmainservice.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PrivateRequestServiceImpl implements PrivateRequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public PrivateRequestServiceImpl(RequestRepository requestRepository,
                                     UserRepository userRepository, EventRepository eventRepository) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;

    }

    @Override
    public List<ParticipationRequestDto> getUserRequestsPrivate(Integer userId) {
        User user = userRepository.getUserById(userId);
        if (user == null) {
            log.error("Пользователь с айди = {} не найден", userId);
            throw new NotFoundException("Пользователь с айди = " + userId + " не найден");
        }
        List<Request> requests = requestRepository.getRequestsByRequesterId(userId);
        return requests.stream()
                .map(RequestMapper::fromRequestToDto)
                .collect(Collectors.toList());
    }
    @Transactional
    @Override
    public ParticipationRequestDto postRequestPrivate(Integer userId, Integer eventId) {
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
        Request request = Request.builder()
                .requester(user)
                .event(event)
                .status(RequestStatus.PENDING)
                .created(LocalDateTime.now())
                .build();
        Request inMemory = requestRepository.save(request);
        return RequestMapper.fromRequestToDto(inMemory);
    }
    @Transactional
    @Override
    public ParticipationRequestDto cancelRequest(Integer reqId, Integer userId) {
        User user = userRepository.getUserById(userId);
        if (user == null) {
            log.error("Пользователь с айди = {} не найден", userId);
            throw new NotFoundException("Пользователь с айди = " + userId + " не найден");
        }
        Request request = requestRepository.getRequestById(reqId);
        if (request == null) {
            log.error("Запрос с айди = {} не найден", reqId);
            throw new NotFoundException("Запрос с айди = " + reqId + " не найден");

        }
        request.setStatus(RequestStatus.CANCELED);
        Request inMemory = requestRepository.save(request);
        return RequestMapper.fromRequestToDto(inMemory);
    }
}
