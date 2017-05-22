package com.dosug.app.controller;

import com.dosug.app.exception.BadRequestException;
import com.dosug.app.respose.model.Response;
import com.dosug.app.respose.viewmodel.UserPreview;
import com.dosug.app.services.authentication.AuthenticationService;
import com.dosug.app.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search/users")
public class UsersSearchController {

    private AuthenticationService authService;

    private UserService userService;

    @GetMapping(value = "/")
    public Response searchEvent(
            @RequestParam(value = "namePart", defaultValue = "") String namePart,
            @RequestParam(value = "count") int count) {

        Response<List<UserPreview>> response = new Response<>();

        if (count <= 0) {
            throw new BadRequestException();
        }

        return response.success(userService
                .getUsersWithPartOfName(namePart, count).stream()
                .map(UserPreview::new)
                .collect(Collectors.toList()));
    }

    @Autowired
    public void setAuthService(AuthenticationService authService) {

        this.authService = authService;
    }

    @Autowired
    public void setUserService(UserService userService) {

        this.userService = userService;
    }
}
