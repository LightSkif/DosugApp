package com.dosug.app.domain;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "tags")
public class Tag {

    @Id @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "tag")
    private String tag;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "event_tag",
            joinColumns = @JoinColumn(name = "tag_id", foreignKey = @ForeignKey(name = "event_tag_tag_id_fk")),
            inverseJoinColumns = @JoinColumn(name = "event_id", foreignKey = @ForeignKey(name = "event_tag_event_id_fk"))
    )
    private Collection<Event> events;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_tag",
            joinColumns = @JoinColumn(name = "tag_id", foreignKey = @ForeignKey(name = "user_tag_tag_id_fk")),
            inverseJoinColumns = @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_tag_user_id_fk"))
    )
    private Collection<User> users;

    public Tag(String tag) {
        this.tag = tag;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Collection<Event> getEvents() {
        return events;
    }

    public void setEvents(Collection<Event> events) {
        this.events = events;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", tag='" + tag + '\'' +
                '}';
    }
}
