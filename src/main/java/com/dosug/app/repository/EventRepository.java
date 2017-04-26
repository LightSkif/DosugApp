package com.dosug.app.repository;

import com.dosug.app.domain.Event;
import org.springframework.data.repository.CrudRepository;


public interface EventRepository extends CrudRepository<Event, String> {

    Event findById(Long eventId);

    Event findByEventName(String eventName);
}
