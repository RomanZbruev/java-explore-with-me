package ru.practicum.ewmmainservice.model;


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
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)
    private Integer id;

    @Column(name = "text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User creator;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(name = "created_on")
    private LocalDateTime createdOn;
}
