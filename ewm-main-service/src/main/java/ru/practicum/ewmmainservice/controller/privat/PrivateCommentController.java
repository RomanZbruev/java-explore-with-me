package ru.practicum.ewmmainservice.controller.privat;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.model.Dto.comment.CommentFullDto;
import ru.practicum.ewmmainservice.model.Dto.comment.NewCommentDto;
import ru.practicum.ewmmainservice.service.privat.PrivateCommentService;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping(path = "/users/{userId}/events/{eventId}/comments")
public class PrivateCommentController {

    private final PrivateCommentService commentService;

    public PrivateCommentController(PrivateCommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public CommentFullDto addNewComment(@PathVariable Integer userId, @PathVariable Integer eventId,
                                        @RequestBody @Valid NewCommentDto newCommentDto) {
        log.info("Получен запрос на добавление нового комментария");
        return commentService.postComment(userId, eventId, newCommentDto);
    }
}
