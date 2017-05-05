package com.dosug.app.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "tag")
    private String tag;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "event_tag",
            joinColumns = @JoinColumn(name = "tag_id", foreignKey = @ForeignKey(name = "event_tag_tag_id_fk")),
            inverseJoinColumns = @JoinColumn(name = "event_id", foreignKey = @ForeignKey(name = "event_tag_event_id_fk"))
    )
    private Set<Event> events;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_tag",
            joinColumns = @JoinColumn(name = "tag_id", foreignKey = @ForeignKey(name = "user_tag_tag_id_fk")),
            inverseJoinColumns = @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_tag_user_id_fk"))
    )
    private Set<User> users;

    public Tag() {
    }

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

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
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
