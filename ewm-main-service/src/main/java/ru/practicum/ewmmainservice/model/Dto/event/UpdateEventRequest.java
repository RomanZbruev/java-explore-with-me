package ru.practicum.ewmmainservice.model.Dto.event;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
public class UpdateEventRequest {

    public static final String FORMATTER = "yyyy-MM-dd HH:mm:ss";

    @NotNull
    private Integer eventId;

    private String annotation;

    private Integer category;

    private String description;

    private Integer participantLimit;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FORMATTER)
    private LocalDateTime eventDate;

    private String title;

    private Boolean paid;
}
