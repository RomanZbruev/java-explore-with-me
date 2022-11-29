package ru.practicum.ewmstats.model.Dto;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ViewStatsDto {

    private String app;

    private String uri;

    private Long hits;
}