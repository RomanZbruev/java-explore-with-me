package ru.practicum.ewmmainservice.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmmainservice.service.admin.AdminCommentService;

@RestController
@Slf4j
@RequestMapping(path = "/admin/comments")
public class AdminCommentController {

    private final AdminCommentService commentService;


    public AdminCommentController(AdminCommentService commentService) {
        this.commentService = commentService;
    }

    @DeleteMapping("/{commentId}")
    public void deleteCommentById(@PathVariable Integer commentId) {
        log.info("Получен запрос от админа на удаления комментария с айди = {} ", commentId);
        commentService.deleteCommentById(commentId);
    }
}
