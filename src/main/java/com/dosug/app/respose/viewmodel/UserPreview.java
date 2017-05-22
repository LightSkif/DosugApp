package com.dosug.app.respose.viewmodel;

import com.dosug.app.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;

// Объект для возвращения основной информации о пользователе на клиент.
public class UserPreview {

    @JsonProperty
    private Long userId;

    @JsonProperty
    private String username;

    @JsonProperty
    private String avatar;


    public UserPreview(User user) {
        userId = user.getId();
        username = user.getUsername();
        avatar = user.getAvatar();
    }
}
