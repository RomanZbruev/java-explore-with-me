package ru.practicum.ewmmainservice.repository;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmmainservice.model.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Comment getCommentById(Integer id);

    List<Comment> findByEvent_Id(Integer eventId, Pageable pageable);

    List<Comment> findByCreator_Id(Integer creatorId, Pageable pageable);
}
