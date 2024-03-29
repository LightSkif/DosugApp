package com.dosug.app.controller;

import com.dosug.app.domain.AuthToken;
import com.dosug.app.form.AuthenticationForm;
import com.dosug.app.response.model.ApiError;
import com.dosug.app.response.model.ApiErrorCode;
import com.dosug.app.response.model.AuthReply;
import com.dosug.app.response.model.Response;
import com.dosug.app.services.authentication.AuthenticationService;
import com.dosug.app.services.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

/**
 * Created by radmir on 22.03.17.
 */

@RestController
public class LoginController {

    private AuthenticationService authService;

    private ValidationService validationService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response login(@RequestBody AuthenticationForm form, Locale locale) {

        Response<AuthReply> response = new Response<>();

        List<ApiError> validateErrors = validationService.validate(form, locale);
        if (!validateErrors.isEmpty()) {
            return response.failure(validateErrors);
        }

        AuthToken token = authService.login(form.getUsername(), form.getPassword());

        if (token == null) {
            //wrong login or password
            return response.failure(
                    new ApiError(ApiErrorCode.WRONG_LOGIN_AND_PASSWORD,
                            "Login or/and password not found"));
        }

        return response.success(new AuthReply(
                authService.authenticate(token.getToken()).getId(),
                token.getToken()));
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
