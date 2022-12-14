package ru.practicum.ewmmainservice.model.Dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewmmainservice.model.RequestStatus;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
public class ParticipationRequestDto {

    public static final String FORMATTER = "yyyy-MM-dd HH:mm:ss";

    private Integer id;

    @NotNull
    private Integer event;

    @NotNull
    private Integer requester;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FORMATTER)
    private LocalDateTime created;

    @NotNull
    private RequestStatus status;

}
