package com.dosug.app.repository;

import com.dosug.app.domain.Event;
import com.dosug.app.domain.EventParticipant;
import com.dosug.app.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface EventParticipantRepository extends CrudRepository<EventParticipant, String> {

    EventParticipant findByEventAndUser(Event event, User user);
}
