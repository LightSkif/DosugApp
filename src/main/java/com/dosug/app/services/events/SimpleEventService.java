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

        if (event == null) {
            throw new EventNotFoundException();
        }

        format(event);

        event.setCreateDate(LocalDateTime.now());

        try {
            // Возвращаем Id созданного события.
            return eventRepository.save(event).getId();
        } catch (Exception e) {

            if (checkEventDuplicate(event)) {
                throw new ConflictException();
            }
            // TODO: Продумать обработку исключения лучше.
            throw e;
        }
    }

    @Override
    public Long updateEvent(Event newEvent, User currentUser) {
        Event updatingEvent = eventRepository.findById(newEvent.getId());

        if (updatingEvent == null) {
            throw new EventNotFoundException();
        }

        // Проверяем, был ли послан запрос на обновление события его создателем.
        if (!currentUser.equals(updatingEvent.getCreator())) {
            throw new InsufficientlyRightsException();
        }

        format(newEvent);

        updatingEvent.setEventName(newEvent.getEventName());
        updatingEvent.setContent(newEvent.getContent());
        updatingEvent.setDateTime(newEvent.getDateTime());
        updatingEvent.setEventName(newEvent.getEventName());
        updatingEvent.setPlaceName(newEvent.getPlaceName());
        updatingEvent.setLongitude(newEvent.getLongitude());
        updatingEvent.setLatitude(newEvent.getLatitude());
        updatingEvent.setTags(newEvent.getTags());

        return eventRepository.save(updatingEvent).getId();
    }

    @Override
    public Event getEvent(Long Id) {

        Event event = eventRepository.findById(Id);

        if (event != null) {
            return event;
        } else {
            throw new EventNotFoundException();
        }
    }

    @Override
    public List<Event> getLastEvents(int count) {

        PageRequest pageable = new PageRequest(0, count);
        return eventRepository.findByOrderByCreateDateDesc(pageable).getContent();
    }

    public List<Event> getLastEventsAfterDateTime(LocalDateTime dateTime) {

        return eventRepository.findByCreateDateGreaterThanOrderByCreateDateDesc(dateTime);
    }

    public List<Event> getLastEventsBeforeDateTime(int count, LocalDateTime dateTime) {

        PageRequest pageable = new PageRequest(0, count);
        return eventRepository.findByCreateDateLessThanOrderByCreateDateDesc(dateTime, pageable).getContent();
    }

    @Override
    public List<Event> getEventsWithPartOfName(String partEventName, int count) {

        PageRequest pageable = new PageRequest(0, count);
        return eventRepository.findByEventNameContainingOrderByCreateDateDesc(partEventName, pageable).getContent();
    }

    @Override
    public List<Event> getEventsWithPartOfNameAfterDateTime(String partEventName, LocalDateTime dateTime) {

        return eventRepository.findByEventNameContainingAndCreateDateGreaterThanOrderByCreateDateDesc(partEventName, dateTime);
    }

    @Override
    public List<Event> getEventsWithPartOfNameBeforeDateTime(String partEventName,
                                                             int count, LocalDateTime dateTime) {

        PageRequest pageable = new PageRequest(0, count);
        return eventRepository.findByEventNameContainingAndCreateDateLessThanOrderByCreateDateDesc(partEventName, dateTime, pageable).getContent();
    }

    @Override
    public List<Event> getAllEventsByCreator(User creator) {

        if (creator != null) {
            return eventRepository.findAllByCreator(creator);
        }

        return null;
    }

    @Override
    public void deleteEvent(long eventId, User currentUser) {

        Event event = eventRepository.findById(eventId);

        if (event == null) {
            throw new EventNotFoundException();
        }

        // Проверяем, был ли послан запрос на удаления события его создателем.
        if (!currentUser.equals(event.getCreator())) {
            throw new InsufficientlyRightsException();
        }


        eventRepository.delete(event);
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
