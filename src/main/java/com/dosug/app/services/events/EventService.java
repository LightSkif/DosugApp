package com.dosug.app.services.events;


import com.dosug.app.domain.Event;
import com.dosug.app.domain.User;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    int PAGE_SIZE = 20;

    Long createEvent(Event event);

    Event getEvent(Long Id);

    List<Event> getLastEventsAfterDateTime(LocalDateTime dateTime);

    List<Event> getEventsBeforeDateTime(int count, LocalDateTime dateTime);

    List<Event> getLastEvents(int count);

    List<Event> getAllEventsByCreator(User creator);

    Boolean deleteEvent(long eventId, User currentUser);
}
