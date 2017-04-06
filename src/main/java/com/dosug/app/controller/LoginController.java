package com.dosug.app.controller;

import com.dosug.app.domain.AuthToken;
import com.dosug.app.form.AuthenticationForm;
import com.dosug.app.respose.model.ErrorForResponse;
import com.dosug.app.respose.model.code.ErrorCode;
import com.dosug.app.respose.model.code.LoginErrorCode;
import com.dosug.app.respose.model.Response;
import com.dosug.app.services.authentication.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by radmir on 22.03.17.
 */

@RestController
public class LoginController {

    private AuthenticationService authService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response login(@Valid @RequestBody AuthenticationForm form,
                          BindingResult bindingResult) {

        Response<String> response = new Response<>();

        if(bindingResult.hasErrors()) {
            return response.failure(getErrors(bindingResult));
        }

        AuthToken token = authService.login(form.getUsername(), form.getPassword());

        if(token == null) {
            //wrong login or password
            return response.failure(
                    new ErrorForResponse(LoginErrorCode.WRONG_LOGIN_AND_PASSWORD,
                            "Login or/and password not found"));
        }

        return response.success(token.getToken());
    }

    /**
     * Return Errors for invalid data
     * @return Errors for Response
     */
    private List<ErrorForResponse> getErrors(BindingResult bindingResult) {
        //TODO: убрать куда нибудь в другое место этот метод

        List<ErrorForResponse> errorsForResponse =
                new LinkedList<>();

        for (FieldError error : bindingResult.getFieldErrors()) {
            ErrorCode errorCode;
            if(error.getField().equals("username")) {
                errorCode = LoginErrorCode.INVALID_USERNAME;
            } else {
                errorCode = LoginErrorCode.INVALID_PASSWORD;
            }

            errorsForResponse.add(
                    new ErrorForResponse(errorCode, error.getDefaultMessage()));
        }

        return errorsForResponse;
    }

    @Autowired
    public void setAuthService(AuthenticationService authService) {
        this.authService = authService;
    }
}
