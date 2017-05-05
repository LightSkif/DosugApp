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

    Event findByEventName(String eventName);

    Page<Event> findByCreateDateBeforeOrderByCreateDate(LocalDateTime dateTime, Pageable pageable);

    Page<Event> findByOrderByCreateDate(Pageable pageable);

    List<Event> findByCreateDateAfterOrderByCreateDate(LocalDateTime dateTime);

    List<Event> findAllByCreator(User creator);
}
