package ru.practicum.ewmmainservice.model.mapper;


import org.springframework.stereotype.Component;
import ru.practicum.ewmmainservice.model.Comment;
import ru.practicum.ewmmainservice.model.Dto.comment.CommentFullDto;
import ru.practicum.ewmmainservice.model.Dto.comment.NewCommentDto;

@Component
public class CommentMapper {

    public static Comment fromNewDtoToComment(NewCommentDto newCommentDto) {
        return Comment.builder()
                .text(newCommentDto.getText())
                .build();
    }

    public static CommentFullDto fromCommentToDto(Comment comment) {
        return CommentFullDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .createdOn(comment.getCreatedOn())
                .name(comment.getCreator().getName())
                .build();
    }

}
