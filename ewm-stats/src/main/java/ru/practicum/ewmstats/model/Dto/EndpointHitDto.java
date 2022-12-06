package ru.practicum.ewmstats.model.Dto;


import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class EndpointHitDto {

    private String app;

    private String uri;

    private String ip;

    private String timestamp;

}
