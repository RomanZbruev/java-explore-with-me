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

    Integer id;

    @NotNull
    Boolean pinned;

    @NotBlank
    String title;

    List<EventShortDto> events;
}
