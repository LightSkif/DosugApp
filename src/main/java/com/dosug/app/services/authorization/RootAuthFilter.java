package com.dosug.app.services.authorization;

import com.dosug.app.services.authentication.AuthenticationService;
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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

@Component("rootAuthFilter")
public class RootAuthFilter extends GenericFilterBean {

    private static final String AUTH_KEY_HEADER = "authKey";

    private AuthenticationService authService;

    private Set excludedPaths = Collections.unmodifiableSet(
            new HashSet<>(asList("/login", "/registration")));

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String authKey = httpRequest.getHeader(AUTH_KEY_HEADER);


        if ( !excludedPaths.contains(httpRequest.getPathInfo()) && // проверяем вдруг этот путь не важен
                (authKey == null || authService.authenticate(authKey) == null)) {

            HttpServletResponse httpResponse = (HttpServletResponse)response;
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    @Autowired
    public void setAuthService(AuthenticationService authService) {
        this.authService = authService;
    }
}
