package com.dosug.app.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;

// Объект для возвращения после авторизации.
public class AuthReply {

    @JsonProperty
    long userId;

    @JsonProperty
    String token;

    public AuthReply(long userId, String token) {
        this.userId = userId;
        this.token = token;
    }
}
