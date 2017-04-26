package com.dosug.app.services.events;

import com.dosug.app.domain.Event;
import com.dosug.app.exception.NullEventException;
import com.dosug.app.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class SimpleEventService implements EventService {

    private EventRepository eventRepository;

    @Override
    public Long createEvent(Event event) {

        if (event == null){
            throw new NullEventException();
        }

        format(event);

        try {
            eventRepository.save(event);
        }
        catch (Exception e) {
            // TODO: Продумать обработку исключения.
        }

        // Возвращаем Id созданного события.
        return eventRepository.findByEventName(event.getEventName()).getId();
    }

    @Override
    public Boolean deleteEvent(Long eventId)
    {
        Event event = eventRepository.findById(eventId);

        if (event == null){
            throw new NullEventException();
        }

        try {
            eventRepository.delete(event);
        }
        catch (Exception e) {
            // TODO: Продумать обработку исключения.
        }

        return true;
    }

    private void format(Event event)
    {
        event.setEventName(event.getEventName().toLowerCase());
    }

    @Autowired
    public void setEventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
}
