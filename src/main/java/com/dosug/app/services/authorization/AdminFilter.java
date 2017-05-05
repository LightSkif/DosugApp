package com.dosug.app.services.authorization;

import com.dosug.app.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author radmirnovii
 */
@Component("adminFilter")
public class AdminFilter extends GenericFilterBean {

    private static final String AUTH_KEY_HEADER = "authKey";

    private AuthorizationService authorizationService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String authKey = httpRequest.getHeader(AUTH_KEY_HEADER);

        if (authKey != null) {
            if (authorizationService.haveRight(authKey, Role.admin())) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        // какойто из if'ов отвалился значит все плохо
        // и пользователь не админ
        rejectRequest(response);

    }

    private void rejectRequest(ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    @Autowired
    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }
}
