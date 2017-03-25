package com.dosug.app.controller;

import com.dosug.app.domain.AuthToken;
import com.dosug.app.form.AuthenticationForm;
import com.dosug.app.services.AuthentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by radmir on 22.03.17.
 */

@RestController
public class LoginController {

    @Autowired
    private AuthentificationService authService;

    @PostMapping("/login")
    public AuthToken login(@Valid AuthenticationForm form, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return null;
        }

        return authService.login(form.getUsername(), form.getPassword());
    }
}
