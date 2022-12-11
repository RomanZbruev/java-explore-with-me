package ru.practicum.ewmmainservice.controller.publ;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.model.Dto.comment.CommentFullDto;
import ru.practicum.ewmmainservice.service.publ.PublicCommentService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
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
    List<CommentFullDto> findCommentsByEventId(@PathVariable Integer eventId,
                                               @RequestParam(required = false, defaultValue = "0")
                                               @PositiveOrZero Integer from,
                                               @RequestParam(required = false, defaultValue = "10")
                                               @Positive Integer size) {
        log.info("Получен запрос на просмотр комментариев события с айди = {} ", eventId);
        return commentService.getCommentsByEventId(eventId, size, from);
    }

    @GetMapping("/users/{userId}")
    List<CommentFullDto> findCommentsByUserId(@PathVariable Integer userId,
                                              @RequestParam(required = false, defaultValue = "0")
                                              @PositiveOrZero Integer from,
                                              @RequestParam(required = false, defaultValue = "10")
                                              @Positive Integer size) {
        log.info("Получен запрос на просмотр комментариев пользователя с айди = {} ", userId);
        return commentService.getCommentsByUserId(userId, size, from);
    }

}
