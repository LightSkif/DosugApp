package com.dosug.app.response.viewmodel.admin;


import com.dosug.app.domain.Tag;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TagView {
    @JsonProperty
    private long id;

    @JsonProperty
    private String tagName;

    public TagView(Tag tag) {
        id = tag.getId();
        tagName = tag.getTagName();
    }
}
