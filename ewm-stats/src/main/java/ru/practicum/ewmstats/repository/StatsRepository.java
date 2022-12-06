package ru.practicum.ewmstats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmstats.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface StatsRepository extends JpaRepository<EndpointHit, Integer> {

    @Query(value = "select app, uri, count(distinct ip) hits " +
            " from stats " +
            " where req_time between ?1 and ?2 " +
            "       and uri in ?3 " +
            " group by app, uri",
            nativeQuery = true)
    List<Object[]> getEndpointHitsUnique(LocalDateTime startFormatted, LocalDateTime endFormatted,
                                         List<String> uris);

    @Query(value = "select app, uri, count(ip) hits " +
            " from stats " +
            " where req_time between ?1 and ?2 " +
            "       and uri in ?3 " +
            " group by app, uri",
            nativeQuery = true)
    List<Object[]> getEndpointHitsNotUnique(LocalDateTime startFormatted, LocalDateTime endFormatted,
                                            List<String> uris);
}
