package com.dosug.app.services.authentication;

import com.dosug.app.domain.AuthToken;
import com.dosug.app.domain.User;
import com.dosug.app.repository.AuthTokenRepository;
import com.dosug.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by radmir on 24.03.17.
 */
@Service
public class SimpleAuthenticationService implements AuthenticationService {

    private UserRepository userRepository;

    private AuthTokenRepository authTokenRepository;

    private AuthTokenProvider authTokenProvider;

    /**
     * return null if username and password wrong
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public AuthToken login(String username, String password) {

        User user = userRepository.findByUsernameAndPassword(username, password);
        //check is user exist in DB
        if (user != null) {
            AuthToken authToken = authTokenProvider.getToken(user);

            authTokenRepository.save(authToken);

            return authToken;
        }
        return null;
    }

    @Override
    public User authenticate(String authKey) {
        AuthToken authToken = authTokenRepository.findOne(authKey);
        if (authToken == null) {
            return null;
        }

        return authToken.getUser();
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setAuthTokenRepository(AuthTokenRepository authTokenRepository) {
        this.authTokenRepository = authTokenRepository;
    }

    @Autowired
    public void setAuthTokenProvider(AuthTokenProvider authTokenProvider) {
        this.authTokenProvider = authTokenProvider;
    }
}
