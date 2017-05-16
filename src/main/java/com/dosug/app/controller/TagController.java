package com.dosug.app.controller;

import com.dosug.app.domain.Tag;
import com.dosug.app.domain.User;
import com.dosug.app.exception.NotAuthorizedException;
import com.dosug.app.exception.UnknownServerException;
import com.dosug.app.respose.model.Response;
import com.dosug.app.services.authentication.AuthenticationService;
import com.dosug.app.services.tags.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/tags")
public class TagController {

    private AuthenticationService authService;

    private TagService tagService;

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response getTags(@RequestParam(value = "tagPart", defaultValue = "") String tagPart,
                            @RequestParam(value = "count", defaultValue = "0") int count,
                            @RequestHeader(value = "authKey") String authKey) {

        Response<List<String>> response = new Response<>();

        User user = authService.authenticate(authKey);
        if (user == null) {
            throw new NotAuthorizedException();
        }

        List<Tag> tags = tagService.getTagsWithPart(tagPart, count);

        if (tags != null) {
            return response.success(
                    tags.stream().map(Tag::getTagName).collect(Collectors.toList()));
        } else {
            throw new UnknownServerException();
        }
    }

    @Autowired
    public void setAuthService(AuthenticationService authService) {
        this.authService = authService;
    }

    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }
}
