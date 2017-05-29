package com.dosug.app.controller;

import com.dosug.app.domain.Tag;
import com.dosug.app.exception.UnknownServerException;
import com.dosug.app.response.model.Response;
import com.dosug.app.services.tags.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/search/tags")
public class TagSearchController {

    private TagService tagService;

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response getTags(@RequestParam(value = "tagPart", defaultValue = "") String tagPart,
                            @RequestParam(value = "count", defaultValue = "0") int count) {

        Response<List<String>> response = new Response<>();

        List<Tag> tags = tagService.getTagsWithPart(tagPart, count);

        if (tags != null) {
            return response.success(
                    tags.stream().map(Tag::getTagName).collect(Collectors.toList()));
        } else {
            throw new UnknownServerException();
        }
    }

    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }
}
