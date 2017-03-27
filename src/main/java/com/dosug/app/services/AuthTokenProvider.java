package com.dosug.app.services;

import com.dosug.app.domain.AuthToken;
import com.dosug.app.domain.User;

/**
 * Created by radmir on 27.03.17.
 */
public interface AuthTokenProvider {

    AuthToken getToken(User user);
}
