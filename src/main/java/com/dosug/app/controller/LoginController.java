package com.dosug.app.controller;

import com.dosug.app.domain.AuthToken;
import com.dosug.app.form.AuthentificationForm;
import com.dosug.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by radmir on 22.03.17.
 */

@RestController
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public AuthToken login(@Valid AuthentificationForm form, BindingResult bindingResult) {

        AuthToken authToken = new AuthToken();

        if(bindingResult.hasErrors()) {
            authToken.setToken("error");
            return authToken;
        }

        if(userRepository.findByUsernameAndPassword(
                form.getUsername(), form.getPassword()) != null) {
            authToken.setToken("all ok!");
            return authToken;
        }

        return authToken;
    }
}
