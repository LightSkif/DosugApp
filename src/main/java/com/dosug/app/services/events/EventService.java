package com.dosug.app.services.events;


import com.dosug.app.domain.Event;
import com.dosug.app.domain.User;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    int PAGE_SIZE = 20;

    Long createEvent(Event event);

    Long updateEvent(Event event, User currentUser);

    void addParticipant(long eventId, User user);

    void removeParticipant(long eventId, User user);

    Event getEvent(long Id);

    boolean isLikedByUser(long eventId, User user);

    List<Event> getLastEventsAfterDateTime(LocalDateTime dateTime);

    List<Event> getLastEventsBeforeDateTime(int count, LocalDateTime dateTime);

    List<Event> getLastEvents(int count);

    List<Event> getEventsWithPartOfName(String partEventName, int count);

    List<Event> getEventsWithPartOfNameAfterDateTime(String partEventName, LocalDateTime dateTime);

    List<Event> getEventsWithPartOfNameBeforeDateTime(String partEventName,
                                                      int count, LocalDateTime dateTime);

    List<Event> getAllEventsByCreator(User creator);

    void deleteEvent(long eventId, User currentUser);
}
