package ru.practicum.ewmmainservice.model.Dto.compilation;


import lombok.*;
import ru.practicum.ewmmainservice.model.Dto.event.EventShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
public class CompilationDto {

    private Integer id;

    @NotNull
    private Boolean pinned;

    @NotBlank
    private String title;

    private List<EventShortDto> events;
}
