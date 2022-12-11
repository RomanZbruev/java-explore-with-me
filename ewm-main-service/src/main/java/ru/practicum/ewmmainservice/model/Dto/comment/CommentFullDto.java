package ru.practicum.ewmmainservice.model.Dto.comment;


import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
public class CommentFullDto {

    Integer id;

    String text;

    String name;

    LocalDateTime createdOn;

}
