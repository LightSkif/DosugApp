package com.dosug.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by radmir on 22.03.17.
 */
@Entity
@Table(name = "auth_tokens")
public class AuthToken {

    @Id
    @Column(name = "auth_token")
    private String token;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    /**
     * if this null then unlimit
     */
    @Column(name = "expiring_time")
    private LocalDateTime expiringTime;

    public AuthToken() {}

    public AuthToken(String token, User user, LocalDateTime createTime, LocalDateTime expiringTime) {
        this.token = token;
        this.user = user;
        this.createTime = createTime;
        this.expiringTime = expiringTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getExpiringTime() {
        return expiringTime;
    }

    public void setExpiringTime(LocalDateTime expiringTime) {
        this.expiringTime = expiringTime;
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
