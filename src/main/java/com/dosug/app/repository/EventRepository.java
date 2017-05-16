package com.dosug.app.repository;

import com.dosug.app.domain.Event;
import com.dosug.app.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface EventRepository extends CrudRepository<Event, String> {

    Event findById(long eventId);

    Event findByEventNameIgnoreCase(String eventName);

    Event findByEventNameIgnoreCaseAndCreatorAndEventDateTime(String eventName, User creator, LocalDateTime eventDateTime);

    Page<Event> findByOrderByCreateDateDesc(Pageable pageable);

    Page<Event> findByCreateDateLessThanOrderByCreateDateDesc(LocalDateTime dateTime,
                                                              Pageable pageable);

    List<Event> findByCreateDateGreaterThanOrderByCreateDateDesc(LocalDateTime dateTime);


    Page<Event> findByEventNameContainingIgnoreCaseOrderByCreateDateDesc(String eventName,
                                                                         Pageable pageable);

    Page<Event> findByEventNameContainingIgnoreCaseAndCreateDateLessThanOrderByCreateDateDesc(String eventName, LocalDateTime dateTime,
                                                                                              Pageable pageable);

    List<Event> findByEventNameContainingIgnoreCaseAndCreateDateGreaterThanOrderByCreateDateDesc(String eventName, LocalDateTime dateTime);


    List<Event> findAllByCreator(User creator);
}
