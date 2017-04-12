package com.dosug.app.controller;

import com.dosug.app.domain.User;
import com.dosug.app.form.RegistrationForm;
import com.dosug.app.respose.model.ApiError;
import com.dosug.app.respose.model.Response;
import com.dosug.app.services.RegistrationService;
import com.dosug.app.services.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author radmirnovii
 */
@RestController
@RequestMapping(value = "/registration")
public class RegistrationController {

    private RegistrationService registrationService;

    private ValidationService validationService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response register(@RequestBody RegistrationForm form) {
        Response<Void> response = new Response<>();

        List<ApiError> validationErrors = validationService.validate(form);
        if( !validationErrors.isEmpty()) {
            return response.failure(validationErrors);
        }

        User user = new User(form.getUsername(), form.getEmail(), form.getPassword());

        List<ApiError> serviceErrors = registrationService.registration(user);
        if( !serviceErrors.isEmpty()) {
            return response.failure(serviceErrors);
        }

        return response.success(null);
    }

    @Autowired
    public void setRegistrationService(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Autowired
    public void setValidationService(ValidationService validationService) {
        this.validationService = validationService;
    }
}
