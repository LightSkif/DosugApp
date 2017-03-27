package com.dosug.app.service;

import com.dosug.app.domain.AuthToken;
import com.dosug.app.domain.User;
import com.dosug.app.repository.AuthTokenRepository;
import com.dosug.app.repository.UserRepository;
import com.dosug.app.services.AuthTokenProvider;
import com.dosug.app.services.SimpleAuthenticationService;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonEncoding;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
/**
 * Created by radmir on 26.03.17.
 */
public class SimpleAuthenticationServiceTest {

    private SimpleAuthenticationService authenticationService;

    @Before
    public void init() {
        this.authenticationService = new SimpleAuthenticationService();
    }

    @Test public void testIfUserNotFound() {
        String username = "user";
        String password = "pass";

        UserRepository repository = mock(UserRepository.class);

        when(repository.findByUsernameAndPassword(username, password))
                .thenReturn(null);

        authenticationService.setUserRepository(repository);

        assertEquals(authenticationService.login(username, password), null);
    }

    @Test public void testThatWeSaveAuthKeyInDB()  {
        User user = new User("user", "email", "pass");

        //userRepository mock
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword()))
                .thenReturn(user);
        authenticationService.setUserRepository(userRepository);

        AuthToken token = new AuthToken();

        //provider mock
        AuthTokenProvider authTokenProvider = mock(AuthTokenProvider.class);
        when(authTokenProvider.getToken(user)).thenReturn(token);
        authenticationService.setAuthTokenProvider(authTokenProvider);

        AuthTokenRepository authTokenRepository= mock(AuthTokenRepository.class);

        authenticationService.setAuthTokenRepository(authTokenRepository);

        authenticationService.login(user.getUsername(), user.getPassword());

        verify(authTokenRepository, times(1)).save(token);
    }
}
