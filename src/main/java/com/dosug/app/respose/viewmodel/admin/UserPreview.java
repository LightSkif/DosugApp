package com.dosug.app.respose.viewmodel.admin;


import com.dosug.app.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserPreview {

    @JsonProperty
    private long id;

    @JsonProperty
    private String username;

    @JsonProperty
    private String email;

    @JsonProperty
    private boolean isBanned;

    public UserPreview(User user) {
        id = user.getId();
        username = user.getUsername();
        email = user.getEmail();
        isBanned = user.isBanned();
    }
}
