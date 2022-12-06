package ru.practicum.ewmmainservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmmainservice.model.Request;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

    @Query("select r " +
            "from Request r " +
            "where (r.event.initiator.id = ?1) and (r.event.id = ?2) " +
            "order by r.created desc")
    List<Request> getRequestsByRequesterAndEvent(Integer requesterId, Integer eventId);

    Request getRequestById(Integer reqId);

    List<Request> getRequestsByRequesterId(Integer reqId);
}
