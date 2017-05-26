package com.dosug.app.controller;

import com.dosug.app.domain.Event;
import com.dosug.app.domain.User;
import com.dosug.app.form.UpdateUserForm;
import com.dosug.app.form.UpdateUserPasswordForm;
import com.dosug.app.form.UserLikeForm;
import com.dosug.app.respose.model.ApiError;
import com.dosug.app.respose.model.Response;
import com.dosug.app.respose.viewmodel.UserView;
import com.dosug.app.respose.viewmodel.UserWithLikePreview;
import com.dosug.app.services.authentication.AuthenticationService;
import com.dosug.app.services.events.EventService;
import com.dosug.app.services.users.UserService;
import com.dosug.app.services.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/users")
public class UserController {

    private AuthenticationService authService;

    private ValidationService validationService;

    private UserService userService;

    private EventService eventService;

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response update(@RequestBody UpdateUserForm form,
                           @RequestHeader(value = "authKey") String authKey) {

        Response<Long> response = new Response<>();

        User requestedUser = authService.authenticate(authKey);

        //return response.success(userService.updateUser(, requestedUser));
        return null;
    }

    @PostMapping(value = "/update-password", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response updatePassword(@RequestBody UpdateUserPasswordForm form,
                                   @RequestHeader(value = "authKey") String authKey) {

        Response<Long> response = new Response<>();

        User requestedUser = authService.authenticate(authKey);

        //return response.success(userService.updateUserPassword(, requestedUser));
        return null;
    }

    @PostMapping(value = "/like")
    public Response addLike(@RequestBody UserLikeForm form,
                            @RequestHeader(value = "authKey") String authKey,
                            Locale locale) {

        Response<Void> response = new Response<>();

        List<ApiError> validateErrors = validationService.validate(form, locale);
        if (!validateErrors.isEmpty()) {
            return response.failure(validateErrors);
        }

        User requestedUser = authService.authenticate(authKey);

        userService.addLike(form.getRatedUserId(), form.getEventId(), form.getTagId(), requestedUser);
        return response.success(null);
    }

    @GetMapping
    public Response getUserId(@RequestParam(value = "id") Long userId) {

        Response<UserView> response = new Response<>();
        return response.success(new UserView(userService.getUser(userId)));
    }

    @GetMapping(value = "/get-participants-likes-by-name")
    public Response getParticipantsWithLikes(@RequestParam(value = "eventId") long eventId,
                                             @RequestParam(value = "count") int count,
                                             @RequestParam(value = "namePart") String namePart,
                                             @RequestHeader(value = "authKey") String authKey) {

        Response<List<UserWithLikePreview>> response = new Response<>();

        User requestedUser = authService.authenticate(authKey);
        Event event = eventService.getEvent(eventId);

        List<UserWithLikePreview> userWithLikePreviews = userService.getParticpantsWithPartName(eventId, count, namePart, requestedUser).stream()
                .map(s -> new UserWithLikePreview(s, event, requestedUser))
                .collect(Collectors.toList());

        return response.success(userWithLikePreviews);
    }

    @GetMapping(value = "/get-participants-likes")
    public Response getParticipantsWithLikes(@RequestParam(value = "eventId") long eventId,
                                             @RequestParam(value = "count") int count,
                                             @RequestHeader(value = "authKey") String authKey) {

        Response<List<UserWithLikePreview>> response = new Response<>();

        return getParticipantsWithLikes(eventId, count, null, authKey);
    }

    @PostMapping(value = "/delete")
    public Response delete(@RequestBody long userId,
                           @RequestHeader(value = "authKey") String authKey) {

        Response<Void> response = new Response<>();

        User requestedUser = authService.authenticate(authKey);

        userService.deleteUser(userId, requestedUser);
        return response.success(null);
    }

    @Autowired
    public void setAuthService(AuthenticationService authService) {

        this.authService = authService;
    }

    @Autowired
    public void setValidationService(ValidationService validationService) {

        this.validationService = validationService;
    }

    @Autowired
    public void setUserService(UserService userService) {

        this.userService = userService;
    }

    @Autowired
    public void setEventService(EventService eventService) {

        this.eventService = eventService;
    }
}
