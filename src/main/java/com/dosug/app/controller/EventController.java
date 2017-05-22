package com.dosug.app.controller;

import com.dosug.app.domain.Event;
import com.dosug.app.domain.Tag;
import com.dosug.app.domain.User;
import com.dosug.app.form.CreateEventForm;
import com.dosug.app.form.UpdateEventForm;
import com.dosug.app.respose.model.ApiError;
import com.dosug.app.respose.model.Response;
import com.dosug.app.respose.viewmodel.EventView;
import com.dosug.app.services.authentication.AuthenticationService;
import com.dosug.app.services.events.EventService;
import com.dosug.app.services.tags.TagService;
import com.dosug.app.services.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/events")
public class EventController {

    private AuthenticationService authService;

    private TagService tagService;

    private ValidationService validationService;

    private EventService eventService;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response create(@RequestBody CreateEventForm form,
                           @RequestHeader(value = "authKey") String authKey,
                           Locale locale) {

        Response<Long> response = new Response<>();

        List<ApiError> validateErrors = validationService.validate(form, locale);
        if (!validateErrors.isEmpty()) {
            return response.failure(validateErrors);
        }

        User user = authService.authenticate(authKey);
        if (user == null) {
            throw new NotAuthorizedException();
        }

        // Преобразуем массив строк из формы в массив тегов.
        Event event = buildEvent(form, user);


        Long eventId = eventService.createEvent(event);

        return response.success(eventId);
    }

    @PostMapping(value = "/update")
    public Response update(@RequestBody UpdateEventForm form,
                           @RequestHeader(value = "authKey") String authKey,
                           Locale locale) {

        Response<Long> response = new Response<>();

        List<ApiError> validateErrors = validationService.validate(form, locale);
        if (!validateErrors.isEmpty()) {
            return response.failure(validateErrors);
        }

        User user = authService.authenticate(authKey);
        if (user == null) {
            throw new NotAuthorizedException();
        }

        Event event = buildEvent(form, user);
        event.setId(form.getEventId());

        return response.success(eventService.updateEvent(event, user));
    }

    @PostMapping(value = "/add-participant", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response addParticipant(@RequestBody long eventId,
                                   @RequestHeader(value = "authKey") String authKey) {
        Response<Void> response = new Response<>();

        User user = authService.authenticate(authKey);
        if (user == null) {
            throw new NotAuthorizedException();
        }

        eventService.addParticipant(eventId, user);

        return response.success(null);
    }

    @PostMapping(value = "/remove-participant", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response removeParticipant(@RequestBody long eventId,
                                      @RequestHeader(value = "authKey") String authKey) {
        Response<Void> response = new Response<>();

        User user = authService.authenticate(authKey);
        if (user == null) {
            throw new NotAuthorizedException();
        }

        eventService.removeParticipant(eventId, user);

        return response.success(null);
    }

    @GetMapping(value = "")
    public Response getEvent(@RequestParam(value = "id") Long eventId) {

        Response<EventView> response = new Response<>();

        User user = authService.authenticate(authKey);
        if (user == null) {
            throw new NotAuthorizedException();
        }

        EventView eventView = new EventView(eventService.getEvent(eventId));
        return response.success(eventView);
    }


//    @GetMapping(value = "/byCreator", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public Response getEventsByCreator(, @RequestHeader(value = "authKey") String authKey) {
//
//        Response<List<Event>> response = new Response<>();
//
//        User user = authService.authenticate(authKey);
//        if (user == null){
//            throw new NotAuthorizedException();
//        }
//
//        return  eventService.getAllEventsByCreator();
//    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response delete(@RequestBody long eventId,
                           @RequestHeader(value = "authKey") String authKey) {

        Response<Void> response = new Response<>();

        User user = authService.authenticate(authKey);
        if (user == null) {
            throw new NotAuthorizedException();
        }

        // Передаём авторизованного пользователя для проверки достаточности прав для удаления.
        eventService.deleteEvent(eventId, user);

        return response.success(null);
    }

    public Event buildEvent(@RequestBody CreateEventForm form, User user) {

        Set<Tag> tags = form.getTags().stream().map(s -> tagService.getTag(s)).collect(Collectors.toSet());

        Event event = new Event();
        event.setCreator(user);
        event.setEventName(form.getEventName());
        event.setContent(form.getContent());
        event.setEventDateTime(form.getDateTime());
        event.setEventName(form.getEventName());
        event.setPlaceName(form.getPlaceName());
        event.setLongitude(form.getLongitude());
        event.setLatitude(form.getLatitude());
        event.setTags(tags);
        return event;
    }

    @Autowired
    public void setAuthService(AuthenticationService authService) {
        this.authService = authService;
    }

    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    @Autowired
    public void setValidationService(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Autowired
    private void setEventService(EventService eventService) {
        this.eventService = eventService;
    }
}
