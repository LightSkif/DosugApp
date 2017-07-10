package com.dosug.app.controller.admin;

import com.dosug.app.form.admin.AdminUpdateEventForm;
import com.dosug.app.repository.EventRepository;
import com.dosug.app.response.model.ApiError;
import com.dosug.app.response.model.Response;
import com.dosug.app.response.viewmodel.admin.EventPreview;
import com.dosug.app.response.viewmodel.admin.EventView;
import com.dosug.app.services.admin.EventAdminService;
import com.dosug.app.services.validation.ValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/admin/events")
@Slf4j
public class EventAdminController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private EventAdminService eventAdminService;

    @GetMapping("/")
    public List<EventPreview> getEvents(@RequestParam(name = "page", defaultValue = "1") int pageNumber,
                                       @RequestParam(name = "usersPerPage", defaultValue = "20") int userPerPage) {

        return eventRepository.findAll(new PageRequest(pageNumber - 1, userPerPage))
                .map(EventPreview::new)
                .getContent();
    }


    @GetMapping("/count")
    public long eventCount() {
        return eventRepository.count();
    }

    @GetMapping("/search")
    public List<EventPreview> searchUsers(@RequestParam(name = "page", defaultValue = "1") int pageNumber,
                                         @RequestParam(name = "usersPerPage", defaultValue = "20") int eventPerPage,
                                         @RequestParam(name = "part") String part) {

        PageRequest pageable = new PageRequest(pageNumber - 1, eventPerPage);

        log.info("search Query: " + part);

        return eventRepository.simpleSearch(part, pageable)
                .map(EventPreview::new)
                .getContent();
    }

    @PostMapping(value = "/{id}/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response updateUser(@RequestBody AdminUpdateEventForm updateForm,
                               @PathVariable(name = "id") long userId,
                               Locale locale) {

        Response res = new Response();

        List<ApiError> apiErrors = validationService.validate(updateForm, locale);
        if(!apiErrors.isEmpty()) {
            return res.failure(apiErrors);
        }

        eventAdminService.updateEvent(updateForm, userId);

        return res.success(null);
    }

    @GetMapping("/search/count")
    public long searchUsersCount(@RequestParam(name = "part") String part) {

        return eventRepository.simpleSearchCount(part);
    }


    @PostMapping("/{id}/ban")
    public Response banUser(@PathVariable(name = "id")long eventId) {
        Response response = new Response();

        eventAdminService.banEvent(eventId);

        return response.success(null);
    }

    @PostMapping("/{id}/allow")
    public Response unbanUser(@PathVariable(name = "id")long eventId) {
        Response response = new Response();

        eventAdminService.allowEvent(eventId);

        return response.success(null);
    }

    @GetMapping("/{id}")
    public EventView getEventById(@PathVariable(name = "id")long eventId) {
        return new EventView(eventRepository.findOne(eventId));
    }

    @PostMapping("/{id}/delete")
    public Response deleteEvent(@PathVariable(name = "id")long eventId) {
        Response response = new Response();

        eventRepository.delete(eventId);

        return response.success(null);
    }

}
