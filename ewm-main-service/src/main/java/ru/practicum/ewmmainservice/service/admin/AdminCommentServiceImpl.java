package ru.practicum.ewmmainservice.service.admin;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmmainservice.exception.NotFoundException;
import ru.practicum.ewmmainservice.model.Comment;
import ru.practicum.ewmmainservice.repository.CommentRepository;

@Service
@Slf4j
public class AdminCommentServiceImpl implements AdminCommentService {

    private final CommentRepository commentRepository;

    public AdminCommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    @Override
    public void deleteCommentById(Integer commentId) {
        Comment comment = commentRepository.getCommentById(commentId);
        if (comment == null) {
            log.error("Комментарий с айди = {} не найден", commentId);
            throw new NotFoundException("Комментарий с айди = " + commentId + " не найден");
        } else {
            log.info("Комментарий с айди = {} удален", commentId);
            commentRepository.deleteById(commentId);
        }
    }
}
