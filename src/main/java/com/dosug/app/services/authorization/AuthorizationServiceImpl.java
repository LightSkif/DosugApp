package com.dosug.app.services.authorization;

import com.dosug.app.domain.Role;
import com.dosug.app.domain.User;
import com.dosug.app.services.authentication.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author radmirnovii
 */
@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private AuthenticationService authenticationService;

    @Autowired
    public AuthorizationServiceImpl(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public boolean haveRight(String authKey, Role role) {
        if (authKey != null) {
            User user = authenticationService.authenticate(authKey);
            if (user != null) {
                boolean isUserAdmin = user.getRoles().stream()
                        .anyMatch(userRole -> userRole.equals(role));

                if (isUserAdmin) {
                    // все получилось
                    return true;
                }
            }
        }

        //где то упали
        return false;
    }

}
