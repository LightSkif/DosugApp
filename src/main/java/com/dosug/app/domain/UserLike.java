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
    @JoinColumn(name = "rated_user_id")
    private User ratedUser;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

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

    public User getRatedUser() {
        return ratedUser;
    }

    public void setRatedUser(User ratedUser) {
        this.ratedUser = ratedUser;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
