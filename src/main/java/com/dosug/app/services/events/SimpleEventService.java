package com.dosug.app.services.events;

import com.dosug.app.domain.Event;
import com.dosug.app.domain.User;
import com.dosug.app.exception.InsufficientlyRightsException;
import com.dosug.app.exception.NullEventException;
import com.dosug.app.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SimpleEventService implements EventService {

    private EventRepository eventRepository;

    @Override
    public Long createEvent(Event event) {

        if (event == null){
            throw new NullEventException();
        }

        format(event);

        event.setCreateDate(LocalDateTime.now());

        try {
            eventRepository.save(event);
        } catch (Exception e) {
            // TODO: Продумать обработку исключения лучше.
            throw e;
        }

        // Возвращаем Id созданного события.
        return eventRepository.findByEventName(event.getEventName()).getId();
    }

    @Override
    public Event getEvent(Integer Id) {
        // TODO: Доделать метод.
        return null;
    }

    @Override
    public List<Event> getEvents(Integer count) {
        // TODO: Доделать метод.
        return null;
    }

    @Override
    public List<Event> getAllEventsByCreator(User creator) {
        // TODO: Доделать метод.
        return null;
    }

    @Override
    public Boolean deleteEvent(Long eventId, User currentUser) {
        Event event = eventRepository.findById(eventId);

        if (event == null){
            throw new NullEventException();
        }

        // Проверяем, был ли послан запрос на удаления события его создателем.
        if (!currentUser.equals(event.getCreator())) {
            throw new InsufficientlyRightsException();
        }

        try {
            eventRepository.delete(event);
        } catch (Exception e) {
            // TODO: Продумать обработку исключения лучше.
            throw e;
        }

        return true;
    }

    private void format(Event event) {
        event.setEventName(event.getEventName().toLowerCase());
    }

    @Autowired
    public void setEventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
}
