package com.dosug.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
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
    private Date createTime;

    /**
     * if this null then unlimit
     */
    @Column(name = "expiring_time")
    private Date expiringTime;

    public AuthToken() {}

    public AuthToken(String token, User user, Date createTime, Date expiringTime) {
        this.token = token;
        this.user = user;
        this.createTime = createTime;
        this.expiringTime = expiringTime;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    @JsonIgnore
    public Date getCreateTime() {
        return createTime;
    }

    @JsonIgnore
    public Date getExpiringTime() {
        return expiringTime;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setExpiringTime(Date expiringTime) {
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
