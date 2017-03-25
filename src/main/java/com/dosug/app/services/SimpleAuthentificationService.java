package com.dosug.app.services;

import com.dosug.app.domain.AuthToken;
import com.dosug.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by radmir on 24.03.17.
 */
@Service
public class SimpleAuthentificationService implements AuthentificationService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public AuthToken login(String username, String password) {
        AuthToken authToken = new AuthToken();

        if(userRepository.findByUsernameAndPassword(username, password) != null) {
            authToken.setToken("all ok!");
        } else {
            authToken.setToken("false");
        }

        return authToken;
    }
}
