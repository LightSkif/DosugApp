package com.dosug.app.services.authentication;

import com.dosug.app.domain.AuthToken;
import com.dosug.app.domain.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

/**
 * generate token by using UUID randomUUID
 */
@Service
public class UUIDAuthTokenProvider implements AuthTokenProvider {

    Random random = new Random();

    @Override
    public AuthToken getToken(User user) {
        String token = UUID.randomUUID().toString();
        return new AuthToken(token, user, LocalDateTime.now(), null);
    }
}
