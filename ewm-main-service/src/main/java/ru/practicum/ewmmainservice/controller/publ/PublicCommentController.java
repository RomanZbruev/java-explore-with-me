package ru.practicum.ewmmainservice.controller.publ;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmmainservice.model.Dto.comment.CommentFullDto;
import ru.practicum.ewmmainservice.service.publ.PublicCommentService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/comments")
public class PublicCommentController {

    private final PublicCommentService commentService;

    public PublicCommentController(PublicCommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{commentId}")
    CommentFullDto findCommentById(@PathVariable Integer commentId) {
        log.info("Получен запрос на просмотр комментария с айди = {} ", commentId);
        return commentService.getCommentById(commentId);
    }

    @GetMapping("/events/{eventId}")
    List<CommentFullDto> findCommentsByEventId(@PathVariable Integer eventId) {
        log.info("Получен запрос на просмотр комментариев события с айди = {} ", eventId);
        return commentService.getCommentsByEventId(eventId);
    }

    @GetMapping("/users/{userId}")
    List<CommentFullDto> findCommentsByUserId(@PathVariable Integer userId) {
        log.info("Получен запрос на просмотр комментариев пользователя с айди = {} ", userId);
        return commentService.getCommentsByUserId(userId);
    }

}
