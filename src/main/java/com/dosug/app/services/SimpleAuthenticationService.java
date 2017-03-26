package com.dosug.app.services;

import com.dosug.app.domain.AuthToken;
import com.dosug.app.repository.UserRepository;
import com.sun.istack.internal.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by radmir on 24.03.17.
 */
@Service
public class SimpleAuthenticationService implements AuthenticationService {

    private UserRepository userRepository;

    /**
     * return null if username and password wrong
     * @param username
     * @param password
     * @return
     */
    @Override
    public AuthToken login(String username, String password) {
        AuthToken authToken = new AuthToken();

        if(userRepository.findByUsernameAndPassword(username, password) != null) {
            authToken.setToken("all ok!");
            return authToken;
        }
        return null;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
