package ru.practicum.ewmmainservice.model.Dto.event;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewmmainservice.model.Location;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
public class NewEventDto {

    @NotBlank
    private String annotation;

    @NotNull
    private Integer category;

    @NotBlank
    private String description;

    private Location location;

    private Integer participantLimit;

    private Boolean requestModeration;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @NotNull
    private Boolean paid;

    @NotBlank
    private String title;

}
