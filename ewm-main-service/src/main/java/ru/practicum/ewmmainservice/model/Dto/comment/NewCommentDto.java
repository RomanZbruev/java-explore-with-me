package ru.practicum.ewmmainservice.model.Dto.comment;


import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
public class NewCommentDto {

    @NotBlank
    private String text;
}
