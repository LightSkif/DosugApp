package com.dosug.app.controller;

import com.dosug.app.domain.User;
import com.dosug.app.respose.model.Response;
import com.dosug.app.services.authentication.AuthenticationService;
import com.dosug.app.services.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/user")
public class UserController {

    private AuthenticationService authService;

    private ValidationService validationService;

    @GetMapping(value = "/get-id")
    public Response getUserId(@RequestHeader(value = "authKey") String authKey) {

        Response<Long> response = new Response<>();

        User user = authService.authenticate(authKey);


        return response.success(user.getId());
    }

    @Autowired
    public void setAuthService(AuthenticationService authService) {
        this.authService = authService;
    }

    @Autowired
    public void setValidationService(ValidationService validationService) {
        this.validationService = validationService;
    }
}
