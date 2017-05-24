package com.dosug.app.controller;

import com.dosug.app.domain.User;
import com.dosug.app.form.UpdateUserForm;
import com.dosug.app.form.UpdateUserPasswordForm;
import com.dosug.app.respose.model.Response;
import com.dosug.app.respose.viewmodel.UserView;
import com.dosug.app.services.authentication.AuthenticationService;
import com.dosug.app.services.users.UserService;
import com.dosug.app.services.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/users")
public class UserController {

    private AuthenticationService authService;

    private ValidationService validationService;

    private UserService userService;

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

    @GetMapping
    public Response getUserId(@RequestParam(value = "id") Long userId) {

        Response<UserView> response = new Response<>();
        return response.success(new UserView(userService.getUser(userId)));
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
}
