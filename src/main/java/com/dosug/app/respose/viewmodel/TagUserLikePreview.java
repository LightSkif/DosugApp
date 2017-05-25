package com.dosug.app.respose.viewmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
 *  Объект для возвращения тега пользователя
 *  с отсутствующей или присутствующей меткой лайка.
 */
public class TagUserLikePreview {

    @JsonProperty
    private Long userId;

    @JsonProperty
    private String tagName;

    @JsonProperty
    private Boolean isLiked;

    public TagUserLikePreview(Long userId, String tagName, Boolean isLiked) {
        this.userId = userId;
        this.tagName = tagName;
        this.isLiked = isLiked;
    }
}
