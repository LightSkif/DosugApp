package com.dosug.app.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by radmir on 22.03.17.
 */
public class AuthToken {

    private String token;

    private User user;

    private Date createTime;

    private Date expiringTime;

    public AuthToken(String token, User user, Date createTime, Date expiringTime) {
        this.token = token;
        this.user = user;
        this.createTime = createTime;
        this.expiringTime = expiringTime;
    }

    @JsonProperty
    public String getToken() {
        return token;
    }

    @JsonProperty
    public User getUser() {
        return user;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getExpiringTime() {
        return expiringTime;
    }

    @Override
    public String toString() {
        return "AuthToken{" +
                "token='" + token + '\'' +
                ", user=" + user.getUsername() +
                ", createTime=" + createTime +
                ", expiringTime=" + expiringTime +
                '}';
    }
}
