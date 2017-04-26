package com.dosug.app.services.events;


import com.dosug.app.domain.Event;

public interface EventService {

    public Long createEvent(Event event);

    public Boolean deleteEvent(Long eventId);
}
