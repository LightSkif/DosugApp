package com.dosug.app.services;

import com.dosug.app.domain.AuthToken;
import com.dosug.app.domain.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * generate token by using UUID randomUUID
 */
@Service
public class UUIDAuthTokenProvider implements AuthTokenProvider{

    Random random = new Random();

    @Override
    public AuthToken getToken(User user) {
        String token = UUID.randomUUID().toString();
        return new AuthToken(token, user, new Date(), null);
    }
}