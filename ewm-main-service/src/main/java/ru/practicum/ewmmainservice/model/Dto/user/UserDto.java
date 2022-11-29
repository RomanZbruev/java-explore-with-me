package ru.practicum.ewmmainservice.model.Dto.user;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
public class UserDto {

    Integer id;

    @NotBlank(message = "Имя не должно быть пустым")
    private String name;

    @NotBlank(message = "Почта не должна быть пустой")
    @Email(message = "Почта должна содержать @")
    private String email;
}
