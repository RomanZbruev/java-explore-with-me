package ru.practicum.ewmmainservice.service.publ;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.exception.NotFoundException;
import ru.practicum.ewmmainservice.model.Comment;
import ru.practicum.ewmmainservice.model.Dto.comment.CommentFullDto;
import ru.practicum.ewmmainservice.model.Event;
import ru.practicum.ewmmainservice.model.User;
import ru.practicum.ewmmainservice.model.mapper.CommentMapper;
import ru.practicum.ewmmainservice.repository.CommentRepository;
import ru.practicum.ewmmainservice.repository.EventRepository;
import ru.practicum.ewmmainservice.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PublicCommentServiceImpl implements PublicCommentService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;

    public PublicCommentServiceImpl(UserRepository userRepository, EventRepository eventRepository,
                                    CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentFullDto getCommentById(Integer commentId) {
        Comment comment = commentRepository.getCommentById(commentId);
        if (comment == null) {
            log.error("Комментарий с айди = {} не найден", commentId);
            throw new NotFoundException("Комментарий с айди = " + commentId + " не найден");
        }
        return CommentMapper.fromCommentToDto(comment);
    }

    @Override
    public List<CommentFullDto> getCommentsByEventId(Integer eventId) {
        Event event = eventRepository.findEventById(eventId);
        if (event == null) {
            log.error("Событие с айди = {} не найдено", eventId);
            throw new NotFoundException("Событие с айди = " + eventId + " не найдено");
        }
        return commentRepository.findByEvent_Id(eventId)
                .stream()
                .map(CommentMapper::fromCommentToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentFullDto> getCommentsByUserId(Integer userId) {
        User user = userRepository.getUserById(userId);
        if (user == null) {
            log.error("Пользователь с айди = {} не найден", userId);
            throw new NotFoundException("Пользователь с айди = " + userId + " не найден");
        }
        return commentRepository.findByCreator_Id(userId)
                .stream()
                .map(CommentMapper::fromCommentToDto)
                .collect(Collectors.toList());
    }
}
