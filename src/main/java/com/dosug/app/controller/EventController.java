package com.dosug.app.controller;

import com.dosug.app.domain.Event;
import com.dosug.app.domain.Tag;
import com.dosug.app.domain.User;
import com.dosug.app.exception.NotAuthorizedException;
import com.dosug.app.form.CreateEventForm;
import com.dosug.app.form.LocalDateTimeDeserializer;
import com.dosug.app.respose.model.ApiError;
import com.dosug.app.respose.model.Response;
import com.dosug.app.respose.viewmodel.EventPreview;
import com.dosug.app.respose.viewmodel.EventView;
import com.dosug.app.services.authentication.AuthenticationService;
import com.dosug.app.services.events.EventService;
import com.dosug.app.services.tags.TagService;
import com.dosug.app.services.validation.ValidationService;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/events")
public class EventController {

    public static final String DEFAULT_EVENT_COUNT = "20";
    private AuthenticationService authService;

    private TagService tagService;

    private ValidationService validationService;

    private EventService eventService;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response create(@RequestBody CreateEventForm form,
                          @RequestHeader(value = "authKey") String authKey) {

        Response<Long> response = new Response<>();

        List<ApiError> validateErrors = validationService.validate(form);
        if (!validateErrors.isEmpty()) {
            return response.failure(validateErrors);
        }

        User user = authService.authenticate(authKey);
        if (user == null){
            throw new NotAuthorizedException();
        }

        // Преобразуем массив строк из формы в массив тегов.
        Set<Tag> tags = form.getTags().stream().map(s -> tagService.getTag(s)).collect(Collectors.toSet());

        Event event = new Event();
        event.setCreator(user);
        event.setEventName(form.getEventName());
        event.setContent(form.getContent());
        event.setDate(form.getDate());
        event.setEventName(form.getEventName());
        event.setPlaceName(form.getPlaceName());
        event.setLongitude(form.getLongitude());
        event.setLatitude(form.getLatitude());
        event.setTags(tags);

        try {
            Long eventId = eventService.createEvent(event);

            return response.success(eventId);
        }
        catch (Exception e) {
            throw e;
        }
    }

    @GetMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response getEvent(@RequestParam(value = "id") Long eventId,
                             @RequestHeader(value = "authKey") String authKey) {

        Response<EventView> response = new Response<>();

        User user = authService.authenticate(authKey);
        if (user == null) {
            throw new NotAuthorizedException();
        }

        EventView eventView = new EventView(eventService.getEvent(eventId));
        return response.success(eventView);
    }

    @GetMapping(value = "/last", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response getLastEvents(
            @RequestParam(value = "count") int count,
            @RequestHeader(value = "authKey") String authKey) {

        Response<List<EventPreview>> response = new Response<>();

        User user = authService.authenticate(authKey);
        if (user == null) {
            throw new NotAuthorizedException();
        }

        return response.success(
                eventService.getLastEvents(count).stream()
                        .map(EventPreview::new)
                        .collect(Collectors.toList()));
    }

    @GetMapping(value = "/last/after", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response getLastEventsAfterDate(
            @RequestParam(value = "datetime")
            @JsonDeserialize(using = LocalDateTimeDeserializer.class)
                    LocalDateTime datetime,
            @RequestHeader(value = "authKey") String authKey) {

        Response<List<EventPreview>> response = new Response<>();

        User user = authService.authenticate(authKey);
        if (user == null) {
            throw new NotAuthorizedException();
        }

        return response.success(
                eventService.getLastEventsAfterDateTime(datetime).stream()
                        .map(EventPreview::new)
                        .collect(Collectors.toList()));
    }

    @GetMapping(value = "/last/before", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response getLastEventsBeforeDate(
            @RequestParam(value = "count") int count,
            @RequestParam(value = "datetime")
            @JsonDeserialize(using = LocalDateTimeDeserializer.class)
                    LocalDateTime datetime,
            @RequestHeader(value = "authKey") String authKey) {

        Response<List<EventPreview>> response = new Response<>();

        User user = authService.authenticate(authKey);
        if (user == null) {
            throw new NotAuthorizedException();
        }

        return response.success(
                eventService.getEventsBeforeDateTime(count, datetime).stream()
                        .map(EventPreview::new)
                        .collect(Collectors.toList()));
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

        Response<Boolean> response = new Response<>();

        User user = authService.authenticate(authKey);
        if (user == null){
            throw new NotAuthorizedException();
        }

        try {
            // Передаём авторизованного пользователя для проверки достаточности прав для удаления.
            boolean result = eventService.deleteEvent(eventId, user);

            return response.success(result);
        }
        catch (Exception e) {
            throw e;
        }
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
