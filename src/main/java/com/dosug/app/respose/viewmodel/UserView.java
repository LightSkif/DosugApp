package com.dosug.app.respose.viewmodel;

import com.dosug.app.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

// Объект для возвращения полной информации о пользователе на клиент.
public class UserView {

    @JsonProperty
    private Long userId;

    @JsonProperty
    private String username;

    @JsonProperty
    private String avatar;

    @JsonProperty
    private String description;

    @JsonProperty
    private List<Long> events;

    @JsonProperty
    private List<String> tags;

    public UserView(User user) {
        userId = user.getId();
        username = user.getUsername();
        avatar = user.getAvatar();
        description = user.getDescription();
        events = user.getEvents().stream().map(s -> s.getId()).collect(Collectors.toList());
        tags = user.getTags().stream().map(s -> s.getTagName()).collect(Collectors.toList());
    }
}
