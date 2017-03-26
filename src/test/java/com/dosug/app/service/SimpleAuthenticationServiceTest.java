package com.dosug.app.service;

import com.dosug.app.repository.UserRepository;
import com.dosug.app.services.SimpleAuthenticationService;
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

    @Test
    public void testIfUserNotFound() {
        String username = "user";
        String password = "pass";

        UserRepository repository = mock(UserRepository.class);

        when(repository.findByUsernameAndPassword(username, password))
                .thenReturn(null);

        authenticationService.setUserRepository(repository);

        assertEquals(authenticationService.login(username, password), null);
    }
}
