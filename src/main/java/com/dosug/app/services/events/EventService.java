package com.dosug.app.services.events;


import com.dosug.app.domain.Event;
import com.dosug.app.domain.User;

public interface EventService {

    Long createEvent(Event event);

    Boolean deleteEvent(Long eventId, User currentUser);
}
