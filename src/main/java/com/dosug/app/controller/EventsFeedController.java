package com.dosug.app.controller;

import com.dosug.app.domain.User;
import com.dosug.app.exception.BadRequestException;
import com.dosug.app.exception.NotAuthorizedException;
import com.dosug.app.respose.model.Response;
import com.dosug.app.respose.viewmodel.EventPreview;
import com.dosug.app.services.authentication.AuthenticationService;
import com.dosug.app.services.events.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

// Класс для получения событий для ленты.
@RestController
@RequestMapping("/feed/events")
public class EventsFeedController {

    private AuthenticationService authService;

    private EventService eventService;

    @GetMapping(value = "/last")
    public Response getLastEvents(
            @RequestParam(value = "count") int count,
            @RequestHeader(value = "authKey") String authKey) {

        Response<List<EventPreview>> response = new Response<>();

        if (count <= 0) {
            throw new BadRequestException();
        }

        User user = authService.authenticate(authKey);
        if (user == null) {
            throw new NotAuthorizedException();
        }

        return response.success(
                eventService.getLastEvents(count).stream()
                        .map(EventPreview::new)
                        .collect(Collectors.toList()));
    }

    @GetMapping(value = "/after")
    public Response getLastEventsAfterDate(
            @RequestParam(value = "dateTime") String dateTime,
            @RequestHeader(value = "authKey") String authKey) {

        Response<List<EventPreview>> response = new Response<>();

        User user = authService.authenticate(authKey);
        if (user == null) {
            throw new NotAuthorizedException();
        }

        return response.success(
                eventService.getLastEventsAfterDateTime(LocalDateTime.parse(dateTime)).stream()
                        .map(EventPreview::new)
                        .collect(Collectors.toList()));
    }

    @GetMapping(value = "/before")
    public Response getLastEventsBeforeDate(
            @RequestParam(value = "count") int count,
            @RequestParam(value = "dateTime") String dateTime,
            @RequestHeader(value = "authKey") String authKey) {

        Response<List<EventPreview>> response = new Response<>();

        if (count <= 0) {
            throw new BadRequestException();
        }

        User user = authService.authenticate(authKey);
        if (user == null) {
            throw new NotAuthorizedException();
        }

        return response.success(
                eventService.getLastEventsBeforeDateTime(count, LocalDateTime.parse(dateTime)).stream()
                        .map(EventPreview::new).collect(Collectors.toList()));
    }

    @Autowired
    public void setAuthService(AuthenticationService authService) {
        this.authService = authService;
    }

    @Autowired
    private void setEventService(EventService eventService) {
        this.eventService = eventService;
    }
}
