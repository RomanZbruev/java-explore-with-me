package ru.practicum.ewmmainservice.service.privat;

import ru.practicum.ewmmainservice.model.Dto.comment.CommentFullDto;
import ru.practicum.ewmmainservice.model.Dto.comment.NewCommentDto;

public interface PrivateCommentService {

    CommentFullDto postComment(Integer userId, Integer eventId, NewCommentDto newCommentDto);
}
