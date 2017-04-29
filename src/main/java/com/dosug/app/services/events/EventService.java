package com.dosug.app.services.events;


import com.dosug.app.domain.Event;
import com.dosug.app.domain.User;

import java.util.List;

public interface EventService {

    Long createEvent(Event event);

    Event getEvent(Integer Id);

    List<Event> getEvents(Integer count);

    List<Event> getAllEventsByCreator(User creator);

    Boolean deleteEvent(Long eventId, User currentUser);
}
