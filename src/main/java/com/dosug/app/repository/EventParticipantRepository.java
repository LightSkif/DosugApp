package com.dosug.app.repository;

import com.dosug.app.domain.Event;
import com.dosug.app.domain.EventParticipant;
import com.dosug.app.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventParticipantRepository extends CrudRepository<EventParticipant, Long> {

    EventParticipant findByEventAndUser(Event event, User user);

    List<EventParticipant> findByEvent(Event event);
}
