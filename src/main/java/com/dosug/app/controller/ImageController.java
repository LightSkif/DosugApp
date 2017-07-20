package com.dosug.app.controller;

import com.dosug.app.domain.User;
import com.dosug.app.exception.ValidateErrorException;
import com.dosug.app.response.model.Response;
import com.dosug.app.services.authentication.AuthenticationService;
import com.dosug.app.services.images.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Locale;

@RestController
@RequestMapping(value = "/images")
public class ImageController {

    @Autowired
    private AuthenticationService authService;

    @Autowired
    private ImageService imageService;

    @PostMapping(value = "/add-to-user")
    public Response addAvatarToUser(@RequestBody MultipartFile file,
                                    @RequestHeader(value = "authKey") String authKey,
                                    Locale locale) {

        Response<String> response = new Response<>();

        User user = authService.authenticate(authKey);

        try {
            return response.success(imageService.saveUserImage(user, file, locale));
        } catch (ValidateErrorException exception) {
            return response.failure(exception.getErrors());
        }
    }

    @PostMapping(value = "/add-to-event")
    public Response addAvatarToEvent(@RequestBody MultipartFile file,
                                     @RequestBody long eventId,
                                     @RequestHeader(value = "authKey") String authKey,
                                     Locale locale) {

        Response<String> response = new Response<>();

        User user = authService.authenticate(authKey);

        try {
            return response.success(imageService.saveEventImage(user, eventId, file, locale));
        } catch (ValidateErrorException exception) {
            return response.failure(exception.getErrors());
        }
    }

    @PostMapping(value = "/{image-name}")
    public Response addAvatarToEvent(@PathVariable(name = "image-name") String imageName,
                                     Locale locale) {

        Response<MultipartFile> response = new Response<>();

        return response.success(imageService.imageLoad(imageName));
    }
}
