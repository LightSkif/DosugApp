package com.dosug.app.services.events;

import com.dosug.app.domain.Event;
import com.dosug.app.domain.EventParticipant;
import com.dosug.app.domain.User;
import com.dosug.app.exception.*;
import com.dosug.app.repository.EventParticipantRepository;
import com.dosug.app.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SimpleEventService implements EventService {

    private EventRepository eventRepository;

    private EventParticipantRepository eventParticipantRepository;

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

            log.error(e.getMessage());
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
        newEvent.setParticipantLinks(updatingEvent.getParticipantLinks());
        newEvent.setImages(updatingEvent.getImages());

        return eventRepository.save(newEvent).getId();
    }

    @Override
    public void addParticipant(long eventId, User user) {
        Event updatingEvent = eventRepository.findById(eventId);
        if (updatingEvent == null) {
            throw new EventNotFoundException();
        }

        // Проверяем не закончилось ли событие.
        if (LocalDateTime.now().isAfter(updatingEvent.getEndDateTime())) {

            throw new InsufficientlyRightsException();
        }

        // Создаём сущность для таблицы связки событий и пользователей.
        EventParticipant eventParticipant = new EventParticipant();
        eventParticipant.setUser(user);
        eventParticipant.setEvent(updatingEvent);
        eventParticipant.setLiked(false);

        EventParticipant existingEventParticipant = eventParticipantRepository.findByEventAndUser(updatingEvent, user);

        if (existingEventParticipant == null) {
            eventParticipantRepository.save(eventParticipant);
        }
    }

    @Override
    public void removeParticipant(long eventId, User user) {
        Event event = eventRepository.findById(eventId);
        if (event == null) {
            throw new EventNotFoundException();
        }

        // Проверяем закончилось ли событие.
        if (LocalDateTime.now().isAfter(event.getEndDateTime())) {

            throw new InsufficientlyRightsException();
        }

        EventParticipant eventParticipant = eventParticipantRepository.findByEventAndUser(event, user);

        if (eventParticipant != null) {
            eventParticipantRepository.delete(eventParticipant);
        }
        // Если не удалось удалить пользователя в списке участников.
        else {
            throw new LinkNotFoundException();
        }
    }

    @Override
    public void addLike(long eventId, User user) {

        Event event = eventRepository.findById(eventId);
        if (event == null) {
            throw new EventNotFoundException();
        }

        // Проверяем закончилось ли событие.
        if (LocalDateTime.now().isBefore(event.getEndDateTime())) {

            throw new InsufficientlyRightsException();
        }

        EventParticipant eventParticipant = eventParticipantRepository.findByEventAndUser(event, user);

        if (eventParticipant != null) {
            if (!eventParticipant.isLiked()) {
                eventParticipant.setLiked(true);
                eventParticipantRepository.save(eventParticipant);

                event.setLikeCount(event.getLikeCount() + 1);
                eventRepository.save(event);
            }
        }
        // Если не удалось удалить пользователя в списке участников.
        else {
            throw new LinkNotFoundException();
        }
    }

    @Override
    public void removeLike(long eventId, User user) {

        Event event = eventRepository.findById(eventId);
        if (event == null) {
            throw new EventNotFoundException();
        }

        // Проверяем закончилось ли событие.
        if (LocalDateTime.now().isBefore(event.getEndDateTime())) {

            throw new InsufficientlyRightsException();
        }

        EventParticipant eventParticipant = eventParticipantRepository.findByEventAndUser(event, user);

        if (eventParticipant != null) {
            if (eventParticipant.isLiked()) {
                eventParticipant.setLiked(false);
                eventParticipantRepository.save(eventParticipant);

                event.setLikeCount(event.getLikeCount() - 1);
                eventRepository.save(event);
            }
        }
        // Если не удалось удалить пользователя в списке участников.
        else {
            throw new LinkNotFoundException();
        }
    }

    @Override
    public boolean isLikedByUser(long eventId, User user) {

        if (user == null) {
            throw new UserNotFoundException();
        }

        Event event = eventRepository.findById(eventId);

        if (event == null) {
            throw new EventNotFoundException();
        }

        // Находим среди участников пользователя, соответствующего user.
        Optional<EventParticipant> eventParticipantOptional = event.getParticipantLinks().stream()
                .filter(s -> s.getUser().equals(user))
                .findFirst();

        // Если участник был найден возвращаем его лайк
        if (eventParticipantOptional.isPresent()) {
            return eventParticipantOptional.get().isLiked();
        } else {
            return false;
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

        PageRequest pageable = new PageRequest(0, DEFAULT_EVENT_PAGE_SIZE);
        return eventRepository.findByCreateDateGreaterThanOrderByCreateDateDesc(dateTime, pageable).getContent();
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

        PageRequest pageable = new PageRequest(0, DEFAULT_EVENT_PAGE_SIZE);
        return eventRepository.findByEventNameContainingIgnoreCaseAndCreateDateGreaterThanOrderByCreateDateDesc(partEventName, dateTime, pageable).getContent();
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
        return eventRepository.findByEventNameIgnoreCaseAndCreatorAndEventDateTime(
                event.getEventName(), event.getCreator(), event.getEventDateTime()) != null;
    }

    @Autowired
    public void setEventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Autowired
    public void setEventParticipantRepository(EventParticipantRepository eventParticipantRepository) {
        this.eventParticipantRepository = eventParticipantRepository;
    }
}
