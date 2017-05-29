package com.dosug.app.domain;

import javax.persistence.*;

@Entity
@Table(name = "user_like")
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getEvaluateUser() {
        return evaluateUser;
    }

    public void setEvaluateUser(User evaluateUser) {
        this.evaluateUser = evaluateUser;
    }

    public UserTag getRatedUserTag() {
        return ratedUserTag;
    }

    public void setRatedUserTag(UserTag ratedUserTag) {
        this.ratedUserTag = ratedUserTag;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
