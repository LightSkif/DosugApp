package com.dosug.app.services.authorization;

import com.dosug.app.domain.Role;

/**
 * собственно сервис который обеспечивает авторизацию
 * @author radmirnovii
 */
public interface AuthorizationService {
    boolean haveRight(String authKey, Role role);
}
