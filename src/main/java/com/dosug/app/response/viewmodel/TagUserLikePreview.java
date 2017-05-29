package com.dosug.app.response.viewmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
 *  Объект для возвращения тега пользователя
 *  с отсутствующей или присутствующей меткой лайка.
 */
public class TagUserLikePreview {

    @JsonProperty
    private Long tagId;

    @JsonProperty
    private String tagName;

    @JsonProperty
    private Boolean isLiked;

    public TagUserLikePreview(Long tagId, String tagName, Boolean isLiked) {
        this.tagId = tagId;
        this.tagName = tagName;
        this.isLiked = isLiked;
    }
}
