package com.dosug.app.services.events;

import com.dosug.app.domain.Event;
import com.dosug.app.domain.User;
import com.dosug.app.exception.ConflictException;
import com.dosug.app.exception.EventNotFoundException;
import com.dosug.app.exception.InsufficientlyRightsException;
import com.dosug.app.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SimpleEventService implements EventService {

    private EventRepository eventRepository;

    @Override
    public Long createEvent(Event event) {

        if (event == null){
            throw new EventNotFoundException();
        }

        format(event);

        event.setCreateDate(LocalDateTime.now());

        try {
            eventRepository.save(event);
        } catch (Exception e) {

            if (checkEventDuplicate(event)) {
                throw new ConflictException();
            }
            // TODO: Продумать обработку исключения лучше.
            throw e;
        }

        // Возвращаем Id созданного события.
        return eventRepository.findByEventName(event.getEventName()).getId();
    }

    @Override
    public Event getEvent(Long Id) {

        return eventRepository.findById(Id);
    }

    public List<Event> getLastEventsAfterDateTime(LocalDateTime dateTime) {

        return eventRepository.findByCreateDateAfterOrderByCreateDate(dateTime);
    }

    public List<Event> getEventsBeforeDateTime(int count, LocalDateTime dateTime) {

        PageRequest pageable = new PageRequest(0, count);
        return eventRepository.findByCreateDateBeforeOrderByCreateDate(dateTime, pageable).getContent();
    }

    @Override
    public List<Event> getLastEvents(int count) {

        PageRequest pageable = new PageRequest(0, count);
        return eventRepository.findByOrderByCreateDate(pageable).getContent();
    }

    @Override
    public List<Event> getAllEventsByCreator(User creator) {

        if (creator != null) {
            return eventRepository.findAllByCreator(creator);
        }

        return null;
    }

    @Override
    public Boolean deleteEvent(long eventId, User currentUser) {

        Event event = eventRepository.findById(eventId);

        if (event == null){
            throw new EventNotFoundException();
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

    private boolean checkEventDuplicate(Event event) {
        return eventRepository.findByEventName(event.getEventName()) != null;
    }

    @Autowired
    public void setEventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
}
