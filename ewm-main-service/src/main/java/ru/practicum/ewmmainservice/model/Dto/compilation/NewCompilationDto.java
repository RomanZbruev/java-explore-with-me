package ru.practicum.ewmmainservice.model.Dto.compilation;

import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

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
public class NewCompilationDto {

    @UniqueElements(message = "Все события подборки должны быть уникальные")
    private List<Integer> events;

    @NotNull
    private Boolean pinned;

    @NotBlank(message = "Заголовок подборки должен быть непустым")
    private String title;


}
