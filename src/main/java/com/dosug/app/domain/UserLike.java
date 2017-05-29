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
    @JoinColumn(name = "evaluate_user_id")
    private User evaluateUser;

    @ManyToOne
    @JoinColumn(name = "rated_user_tag_id")
    private UserTag ratedUserTag;

    @ManyToOne
    @JoinColumn(name = "event_id")
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
