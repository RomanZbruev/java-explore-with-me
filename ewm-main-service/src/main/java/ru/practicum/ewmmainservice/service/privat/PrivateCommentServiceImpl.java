package ru.practicum.ewmmainservice.service.privat;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmmainservice.exception.BadRequestException;
import ru.practicum.ewmmainservice.exception.NotFoundException;
import ru.practicum.ewmmainservice.model.Comment;
import ru.practicum.ewmmainservice.model.Dto.comment.CommentFullDto;
import ru.practicum.ewmmainservice.model.Dto.comment.NewCommentDto;
import ru.practicum.ewmmainservice.model.Event;
import ru.practicum.ewmmainservice.model.User;
import ru.practicum.ewmmainservice.model.mapper.CommentMapper;
import ru.practicum.ewmmainservice.repository.CommentRepository;
import ru.practicum.ewmmainservice.repository.EventRepository;
import ru.practicum.ewmmainservice.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@Slf4j
public class PrivateCommentServiceImpl implements PrivateCommentService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;

    public PrivateCommentServiceImpl(UserRepository userRepository, EventRepository eventRepository,
                                     CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    @Override
    public CommentFullDto postComment(Integer userId, Integer eventId, NewCommentDto newCommentDto) {
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
        if (event.getInitiator().getId().equals(userId)) {
            log.error("Инициатор события не может оставлять комментарии на свои события");
            throw new BadRequestException("Инициатор события не может оставлять комментарии на свои события");
        }

        Comment comment = CommentMapper.fromNewDtoToComment(newCommentDto);
        comment.setCreatedOn(LocalDateTime.now());
        comment.setCreator(user);
        comment.setEvent(event);

        Comment inMemory = commentRepository.save(comment);
        log.info("Комментарий с айди = {} добавлен", comment.getId());
        return CommentMapper.fromCommentToDto(inMemory);
    }
}
