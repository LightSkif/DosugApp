package com.dosug.app.services.admin;

import com.dosug.app.domain.Event;
import com.dosug.app.form.admin.AdminUpdateEventForm;
import com.dosug.app.repository.EventRepository;
import com.dosug.app.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class SimpleEventAdminService implements EventAdminService{


    private EventRepository eventRepository;

    private UserRepository userRepository;

    @Override
    public void banEvent(long eventId) {
        Optional.ofNullable(eventRepository.findOne(eventId))
                .ifPresent(event -> {
                    event.setAllowed(false);
                    eventRepository.save(event);
                });
    }

    @Override
    public void allowEvent(long eventId) {
        Optional.ofNullable(eventRepository.findOne(eventId))
                .ifPresent(event -> {
                    event.setAllowed(true);
                    eventRepository.save(event);
                });
    }

    @Override
    public void updateEvent(AdminUpdateEventForm form, long eventId) {
       Event event = eventRepository.findOne(eventId);

       if(event != null) {
           event.setEventName(form.getEventName());
           event.setLatitude(form.getLatitude());
           event.setLongitude(form.getLongitude());
           event.setContent(form.getContent());
           event.setEventDateTime(form.getDateTime());
           event.setPlaceName(form.getPlaceName());
           try {
               eventRepository.save(event);
           } catch (Exception e) {
               log.error("Cannot save form");
               throw e;
           }
       }
    }


    @Autowired
    public void setEventRepository(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
}
