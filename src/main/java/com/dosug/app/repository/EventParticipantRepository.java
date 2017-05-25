package com.dosug.app.repository;

import com.dosug.app.domain.Event;
import com.dosug.app.domain.EventParticipant;
import com.dosug.app.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface EventParticipantRepository extends CrudRepository<EventParticipant, Long> {

    EventParticipant findByEventAndUser(Event event, User user);

    //@Query
    Page<EventParticipant> findByEvent(Event event, Pageable pageable);
}
