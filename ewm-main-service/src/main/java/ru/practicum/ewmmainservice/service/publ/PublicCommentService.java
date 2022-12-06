package ru.practicum.ewmmainservice.service.publ;


import ru.practicum.ewmmainservice.model.Dto.comment.CommentFullDto;

import java.util.List;

public interface PublicCommentService {

    CommentFullDto getCommentById(Integer commentId);

    List<CommentFullDto> getCommentsByEventId(Integer eventId);

    List<CommentFullDto> getCommentsByUserId(Integer userId);
}
