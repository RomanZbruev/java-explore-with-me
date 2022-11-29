package ru.practicum.ewmmainservice.model;

import lombok.*;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
public class Location {

    @NotNull
    private Float lat;

    @NotNull
    private Float lon;
}
