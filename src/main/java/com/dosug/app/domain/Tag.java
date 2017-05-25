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

    @Column(name = "tag_name")
    private String tagName;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "event_tag",
            joinColumns = @JoinColumn(name = "tag_id", foreignKey = @ForeignKey(name = "event_tag_tag_id_fk")),
            inverseJoinColumns = @JoinColumn(name = "event_id", foreignKey = @ForeignKey(name = "event_tag_event_id_fk"))
    )
    private Set<Event> events;

    @OneToMany(targetEntity = UserLike.class,
            mappedBy = "evaluateUser",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER)
    private Set<UserLike> likes;

    @OneToMany(targetEntity = UserTag.class,
            mappedBy = "tag",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER)
    private Set<UserTag> userLinks;

    public Tag() {
    }

    public Tag(String tagName) {
        this.tagName = tagName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public Set<UserLike> getLikes() {
        return likes;
    }

    public void setLikes(Set<UserLike> likes) {
        this.likes = likes;
    }

    public Set<UserTag> getUserLinks() {
        return userLinks;
    }

    public void setUserLinks(Set<UserTag> userLinks) {
        this.userLinks = userLinks;
    }

    public Set<UserTag> getUsers() {
        return userLinks;
    }

    public void setUsers(Set<UserTag> userLinks) {
        this.userLinks = userLinks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        return getTagName() != null ? getTagName().equals(tag.getTagName()) : tag.getTagName() == null;
    }

    @Override
    public int hashCode() {
        return getTagName() != null ? getTagName().hashCode() : 0;
    }
}
