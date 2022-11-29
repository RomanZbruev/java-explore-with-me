package ru.practicum.ewmstats.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "stats")
public class EndpointHit {
    @Id
    @Column(name = "endpoint_hits_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "app", nullable = false, length = 255)
    private String app;

    @Column(name = "uri", nullable = false, length = 255)
    private String uri;

    @Column(name = "ip", nullable = false, length = 255)
    private String ip;

    @Column(name = "req_time", nullable = false)
    private LocalDateTime timestamp;
}