package com.dosug.app.services;

import com.dosug.app.domain.AuthToken;

/**
 * Created by radmir on 24.03.17.
 */
public interface AuthentificationService {

    AuthToken login(String username, String password);

}
