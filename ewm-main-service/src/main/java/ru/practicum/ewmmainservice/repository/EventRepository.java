package ru.practicum.ewmmainservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmmainservice.model.Category;
import ru.practicum.ewmmainservice.model.Event;
import ru.practicum.ewmmainservice.model.EventState;
import ru.practicum.ewmmainservice.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    public Event findEventById(Integer id);

    @Query("select e " +
            "from Event e " +
            "where e.id in ?1 " +
            "group by e.id " +
            "order by e.eventDate desc")
    public Set<Event> findEventsByIds(List<Integer> ids);

    public List<Event> findEventsByInitiatorId(Integer id, Pageable pageable);

    @Query("select e " +
            "from Event e " +
            "where e.category in ?1 " +
            "and e.paid = ?2 " +
            "and e.eventDate between ?3 and ?4 " +
            "and (e.participantLimit - e.confirmedRequests) > 0 " +
            "group by e.id " +
            "order by e.eventDate desc")
    Page<Event> getAllEventsByEventDateAvailablePublic(List<Category> categoryList,
                                                       Boolean paid, LocalDateTime rangeStart,
                                                       LocalDateTime rangeEnd, Pageable pageable);

    @Query("select e " +
            "from Event e " +
            "where e.category in ?1 " +
            "and e.paid = ?2 " +
            "and e.eventDate between ?3 and ?4 " +
            "and (e.participantLimit - e.confirmedRequests) > 0 " +
            "group by e.id " +
            "order by e.id asc ")
    Page<Event> getAllEventsByViewsAvailablePublic(List<Category> categoryList, Boolean paid,
                                                   LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                   Pageable pageable);

    @Query("select e " +
            "from Event e " +
            "where upper(e.annotation) like upper(concat('%',?1,'%')) " +
            "or upper(e.description) like upper(concat('%',?1,'%')) " +
            "and e.category in ?2 " +
            "and e.paid = ?3 " +
            "and e.eventDate between ?4 and ?5 " +
            "and (e.participantLimit - e.confirmedRequests) > 0 " +
            "group by e.id " +
            "order by e.eventDate desc")
    Page<Event> getAllEventsByEventDateAvailableWithTextPublic(String text, List<Category> categoryList, Boolean paid,
                                                               LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                               Pageable pageable);

    @Query("select e " +
            "from Event e " +
            "where upper(e.annotation) like upper(concat('%',?1,'%')) " +
            "or upper(e.description) like upper(concat('%',?1,'%')) " +
            "and e.category in ?2 " +
            "and e.paid = ?3 " +
            "and e.eventDate between ?4 and ?5 " +
            "and (e.participantLimit - e.confirmedRequests) > 0 " +
            "group by e.id " +
            "order by e.id asc ")
    Page<Event> getAllEventsByViewsAvailableWithTextPublic(String text, List<Category> categoryList, Boolean paid,
                                                           LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                           Pageable pageable);

    @Query("select e " +
            "from Event e " +
            "where e.category in ?1 " +
            "and e.paid = ?2 " +
            "and e.eventDate between ?3 and ?4 " +
            "group by e.id " +
            "order by e.eventDate desc")
    Page<Event> getAllEventsByEventDatePublic(List<Category> categoryList,
                                              Boolean paid, LocalDateTime rangeStart,
                                              LocalDateTime rangeEnd, Pageable pageable);

    @Query("select e " +
            "from Event e " +
            "where e.category in ?1 " +
            "and e.paid = ?2 " +
            "and e.eventDate between ?3 and ?4 " +
            "group by e.id " +
            "order by e.id asc")
    Page<Event> getAllEventsByViewsPublic(List<Category> categoryList, Boolean paid,
                                          LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                          Pageable pageable);

    @Query("select e " +
            "from Event e " +
            "where upper(e.annotation) like upper(concat('%',?1,'%')) " +
            "or upper(e.description) like upper(concat('%',?1,'%')) " +
            "and e.category in ?2 " +
            "and e.paid = ?3 " +
            "and e.eventDate between ?4 and ?5 " +
            "group by e.id " +
            "order by e.eventDate desc")
    Page<Event> getAllEventsByEventDateWithTextPublic(String text, List<Category> categoryList, Boolean paid,
                                                      LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                      Pageable pageable);

    @Query("select e " +
            "from Event e " +
            "where upper(e.annotation) like upper(concat('%',?1,'%')) " +
            "or upper(e.description) like upper(concat('%',?1,'%')) " +
            "and e.category in ?2 " +
            "and e.paid = ?3 " +
            "and e.eventDate between ?4 and ?5 " +
            "group by e.id " +
            "order by e.id")
    Page<Event> getAllEventsByViewsWithTextPublic(String text, List<Category> categoryList, Boolean paid,
                                                  LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                  Pageable pageable);


    @Query("select e " +
            "from Event e " +
            "where e.initiator in ?1 " +
            "and e.category in ?2 " +
            "and e.state in ?3 " +
            "and e.eventDate between ?4 and ?5 " +
            "group by e.id " +
            "order by e.eventDate desc")
    Page<Event> getEventsAdmin(List<User> userList, List<Category> categoryList, List<EventState> eventStateList,
                               LocalDateTime rangeStart, LocalDateTime rangeEnd,
                               Pageable pageable);
}
