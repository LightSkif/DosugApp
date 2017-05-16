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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        newEvent.setCreateDate(updatingEvent.getCreateDate());
        newEvent.setAllowed(null);
        newEvent.setParticipants(updatingEvent.getParticipants());
        newEvent.setImages(updatingEvent.getImages());

        return eventRepository.save(newEvent).getId();
    }

    @Override
    public void addParticipant(long eventId, User user) {
        Event updatingEvent = eventRepository.findById(eventId);
        if (updatingEvent == null) {
            throw new EventNotFoundException();
        }

        if (updatingEvent.getParticipants().add(user)) {
            eventRepository.save(updatingEvent);
        }
        // Если пользователь уже добавлен в список участников.
        else {
            throw new ConflictException();
        }
    }

    @Override
    public void removeParticipant(long eventId, User user) {
        Event updatingEvent = eventRepository.findById(eventId);
        if (updatingEvent == null) {
            throw new EventNotFoundException();
        }


        if (updatingEvent.getParticipants().remove(user)) {
            eventRepository.save(updatingEvent);
        }
        // Если не удалось удалить пользователя из списка участников.
        else {
            throw new ConflictException();
        }
    }

    @Override
    public Event getEvent(long Id) {

        Event event = eventRepository.findById(Id);

        if (event != null) {
            return event;
        } else {
            throw new EventNotFoundException();
        }
    }

    /**
     * В случае если в базе не найдено подходящих событий возвращаем пустой список.
     */
    @Override
    public List<Event> getLastEvents(int count) {

        PageRequest pageable = new PageRequest(0, count);
        return eventRepository.findByOrderByCreateDateDesc(pageable).getContent();
    }

    /**
     * В случае если в базе не найдено подходящих событий возвращаем пустой список.
     */
    @Override
    public List<Event> getLastEventsAfterDateTime(LocalDateTime dateTime) {

        return eventRepository.findByCreateDateGreaterThanOrderByCreateDateDesc(dateTime).stream()
                .limit(20)
                .collect(Collectors.toList());
    }

    /** В случае если в базе не найдено подходящих событий возвращаем пустой список. */
    @Override
    public List<Event> getLastEventsBeforeDateTime(int count, LocalDateTime dateTime) {

        PageRequest pageable = new PageRequest(0, count);
        return eventRepository.findByCreateDateLessThanOrderByCreateDateDesc(dateTime, pageable).getContent();
    }

    /** В случае если в базе не найдено подходящих событий возвращаем пустой список. */
    @Override
    public List<Event> getEventsWithPartOfName(String partEventName, int count) {

        PageRequest pageable = new PageRequest(0, count);
        return eventRepository.findByEventNameContainingIgnoreCaseOrderByCreateDateDesc(partEventName, pageable).getContent();
    }

    /** В случае если в базе не найдено подходящих событий возвращаем пустой список. */
    @Override
    public List<Event> getEventsWithPartOfNameAfterDateTime(String partEventName, LocalDateTime dateTime) {

        return eventRepository.findByEventNameContainingIgnoreCaseAndCreateDateGreaterThanOrderByCreateDateDesc(partEventName, dateTime).stream()
                .limit(20)
                .collect(Collectors.toList());
    }

    /** В случае если в базе не найдено подходящих событий возвращаем пустой список. */
    @Override
    public List<Event> getEventsWithPartOfNameBeforeDateTime(String partEventName,
                                                             int count,
                                                             LocalDateTime dateTime) {

        PageRequest pageable = new PageRequest(0, count);
        return eventRepository.findByEventNameContainingIgnoreCaseAndCreateDateLessThanOrderByCreateDateDesc(partEventName, dateTime, pageable).getContent();
    }

    /** В случае если creator равен null возвращаем пустой список. */
    @Override
    public List<Event> getAllEventsByCreator(User creator) {

        if (creator != null) {
            return eventRepository.findAllByCreator(creator);
        }

        return new ArrayList<>();
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
        // TODO: придумать форматирование, если хочется.
    }

    private boolean checkEventDuplicate(Event event) {
        return eventRepository.findByEventNameIgnoreCaseAndCreatorAndEventDateTime(event.getEventName(), event.getCreator(), event.getEventDateTime()) != null;
    }

    @Autowired
    public void setEventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
}
