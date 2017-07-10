package com.dosug.app.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by radmir on 22.03.17.
 */
@Entity
@Table(name = "auth_tokens")
@Data
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

    public AuthToken() {
    }

    public AuthToken(String token, User user, LocalDateTime createTime, LocalDateTime expiringTime) {
        this.token = token;
        this.user = user;
        this.createTime = createTime;
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
