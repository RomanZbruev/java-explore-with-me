package ru.practicum.ewmmainservice.model.Dto.event;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewmmainservice.model.Dto.category.CategoryDto;
import ru.practicum.ewmmainservice.model.Dto.comment.CommentFullDto;
import ru.practicum.ewmmainservice.model.Dto.user.UserShortDto;
import ru.practicum.ewmmainservice.model.EventState;
import ru.practicum.ewmmainservice.model.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
public class EventFullDto {

    public static final String FORMATTER = "yyyy-MM-dd HH:mm:ss";

    private Integer id;

    @NotBlank
    private String annotation;

    @NotNull
    private CategoryDto category;

    private String description;

    private Location location;

    private Integer participantLimit;

    private Boolean requestModeration;

    private EventState state;

    private Integer confirmedRequests;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FORMATTER)
    private LocalDateTime createdOn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FORMATTER)
    private LocalDateTime publishedOn;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FORMATTER)
    private String eventDate;

    @NotNull
    private UserShortDto initiator;

    @NotNull
    private Boolean paid;

    @NotBlank
    private String title;

    private List<CommentFullDto> commentList;

    private Integer views;
}
