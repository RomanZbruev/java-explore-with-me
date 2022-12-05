package ru.practicum.ewmmainservice.model.Dto.event;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewmmainservice.model.Location;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
public class AdminUpdateEventRequest {

    public static final String FORMATTER = "yyyy-MM-dd HH:mm:ss";

    private String annotation;

    private Integer category;

    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FORMATTER)
    private LocalDateTime eventDate;

    private Location location;

    private Boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration;

    private String title;
}
