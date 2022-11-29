package ru.practicum.ewmmainservice.model.Dto.user;

import lombok.*;


@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
public class UserShortDto {

    private Integer id;

    private String name;


}
