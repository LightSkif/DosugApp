package com.dosug.app.services.authentication;

import com.dosug.app.domain.AuthToken;
import com.dosug.app.domain.User;

/**
 * Created by radmir on 24.03.17.
 */
public interface AuthenticationService {

    AuthToken login(String username, String password);

    /**
     * идентификация пользователя
     *
     * @param authKey ключ аутентификации
     * @return null если таких нет
     */
    User authenticate(String authKey);
}
