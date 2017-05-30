package com.dosug.app.response.viewmodel;

import com.dosug.app.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Объект для возвращения полной информации о пользователе на клиент.
 */
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
    private String firstName;

    @JsonProperty
    private String lastName;

    @JsonProperty
    private List<Long> events;

    @JsonProperty
    private List<String> tags;

    public UserView(User user) {
        userId = user.getId();
        username = user.getUsername();
        avatar = user.getAvatar();
        description = user.getDescription();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        events = user.getEventLinks().stream().map(s -> s.getEvent().getId()).collect(Collectors.toList());
        tags = user.getTagLinks().stream().map(s -> s.getTag().getTagName()).collect(Collectors.toList());
    }
}
