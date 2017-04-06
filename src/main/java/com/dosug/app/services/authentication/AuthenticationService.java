package com.dosug.app.services.authentication;

import com.dosug.app.domain.AuthToken;

/**
 * Created by radmir on 24.03.17.
 */
public interface AuthenticationService {

    AuthToken login(String username, String password);

}
