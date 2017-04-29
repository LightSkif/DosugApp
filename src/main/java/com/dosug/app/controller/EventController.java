package com.dosug.app.controller;

import com.dosug.app.domain.Event;
import com.dosug.app.domain.Tag;
import com.dosug.app.domain.User;
import com.dosug.app.exception.NotAuthorizedException;
import com.dosug.app.form.CreateEventForm;
import com.dosug.app.respose.model.ApiError;
import com.dosug.app.respose.model.Response;
import com.dosug.app.services.authentication.AuthenticationService;
import com.dosug.app.services.events.EventService;
import com.dosug.app.services.tags.TagService;
import com.dosug.app.services.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        List<Tag> tags = form.getTags().stream().map(s -> tagService.getTag(s)).collect(Collectors.toList());

        Event event = new Event();
        event.setCreator(user);
        event.setEventName(form.getEventName());
        event.setContent(form.getContent());
        event.setDate(form.getDate());
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

//    @GetMapping(value = "/test")
//    public Response test() {
//
//        ArrayList<String> tags = new ArrayList<String>();
//        tags.add("tag1");
//        tags.add("tag2");
//        Response<CreateEventForm> response = new Response<>();
//        return response.success(new CreateEventForm("concert", "powerwolf", LocalDateTime.now(), 5, 5, tags));
//    }
//
//    @GetMapping(value = "/test2")
//    public Response test2() {
//        Response<String> response = new Response<>();
//        return response.success("hey");
//    }

//    @PostMapping(value = "/getevent", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public Response getEvent(@RequestBody Long eventId,
//                           @RequestHeader(value = "authKey") String authKey) {
//
//        Response<Event> response = new Response<>();
//
//        User user = authService.authenticate(authKey);
//        if (user == null){
//            throw new NotAuthorizedException();
//        }
//
//        return  eventService.
//    }
//
//    @PostMapping(value = "/getmyevent", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public Response getMyEvent(@RequestBody Long eventId,
//                             @RequestHeader(value = "authKey") String authKey) {
//
//        Response<List<Event>> response = new Response<>();
//
//        User user = authService.authenticate(authKey);
//        if (user == null){
//            throw new NotAuthorizedException();
//        }
//
//        return  eventService.
//    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response delete(@RequestBody Long eventId,
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
