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
        Event event = new Event(user, form.getEventName(),
                          form.getContent(), form.getDate(),
                          form.getAltitude(), form.getLatitude(),
                          tags);

        Long eventId = eventService.createEvent(event);

        return response.success(eventId);
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response delete(@RequestBody Long eventId,
                           @RequestHeader(value = "authKey") String authKey) {

        Response<Boolean> response = new Response<>();

        User user = authService.authenticate(authKey);
        if (user == null){
            throw new NotAuthorizedException();
        }

        boolean result = eventService.deleteEvent(eventId);

        return response.success(result);
    }

    @Autowired
    public void setAuthService(AuthenticationService authService) {
        this.authService = authService;
    }

    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    @Autowired
    public void setValidationService(ValidationService validationService) {
        this.validationService = validationService;
    }
}
