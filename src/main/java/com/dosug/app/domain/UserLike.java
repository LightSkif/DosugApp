package com.dosug.app.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user_like")
@Data
public class UserLike {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "evaluate_user_id",
            foreignKey = @ForeignKey(name = "user_like_evaluate_user_id_fk"))
    private User evaluateUser;

    @ManyToOne
    @JoinColumn(name = "rated_user_tag_id",
            foreignKey = @ForeignKey(name = "user_like_rated_user_tag_id_fk"))
    private UserTag ratedUserTag;

    @ManyToOne
    @JoinColumn(name = "event_id",
            foreignKey = @ForeignKey(name = "user_like_event_id_fk"))
    private Event event;
}
