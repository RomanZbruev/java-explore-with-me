package ru.practicum.ewmmainservice.model.Dto.category;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
public class NewCategoryDto {

    @NotBlank(message = "Название категории не должно быть пустым")
    private String name;
}
