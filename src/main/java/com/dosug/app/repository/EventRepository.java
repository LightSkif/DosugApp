package com.dosug.app.repository;

import com.dosug.app.domain.Event;
import com.dosug.app.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface EventRepository extends PagingAndSortingRepository<Event, Long> {

    @Query("FROM Event as e WHERE e.eventName LIKE CONCAT('%', :part, '%')")
    Page<Event> simpleSearch(@Param("part") String part, Pageable pageable);

    @Query("SELECT COUNT(*) FROM Event as e WHERE e.eventName LIKE CONCAT('%', :part, '%')")
    long simpleSearchCount(@Param("part") String part);

    Event findById(long eventId);

    Event findByEventNameIgnoreCase(String eventName);

    Event findByEventNameIgnoreCaseAndCreatorAndEventDateTime(String eventName, User creator, LocalDateTime eventDateTime);

    Page<Event> findByOrderByCreateDateDesc(Pageable pageable);

    Page<Event> findByCreateDateLessThanOrderByCreateDateDesc(LocalDateTime dateTime,
                                                              Pageable pageable);

    Page<Event> findByCreateDateGreaterThanOrderByCreateDateDesc(LocalDateTime dateTime,
                                                                 Pageable pageable);


    Page<Event> findByEventNameContainingIgnoreCaseOrderByCreateDateDesc(String eventName,
                                                                         Pageable pageable);

    Page<Event> findByEventNameContainingIgnoreCaseAndCreateDateLessThanOrderByCreateDateDesc(String eventName, LocalDateTime dateTime,
                                                                                              Pageable pageable);

    Page<Event> findByEventNameContainingIgnoreCaseAndCreateDateGreaterThanOrderByCreateDateDesc(String eventName, LocalDateTime dateTime,
                                                                                                 Pageable pageable);


    List<Event> findAllByCreator(User creator);
}
