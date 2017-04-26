package com.dosug.app.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * A long time ago in a galaxy far, far away...
 */
@Entity
@Table(name = "events")
public class Event {

    @Id @GeneratedValue
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id")
    private User creator;

    @Column(name = "event_name")
    private String eventName;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    @Column(name = "content")
    private String content;

    @Column(name = "event_date")
    private LocalDateTime date;

    @Column(name = "altitude")
    private double altitude;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "allowed")
    private boolean allowed;

    @OneToMany(targetEntity = Image.class,
            cascade = CascadeType.ALL,
            mappedBy = "event")
    private Collection<Image> images;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "event_participant",
            joinColumns = @JoinColumn(name = "event_id", foreignKey = @ForeignKey(name = "event_participant_event_id_fk")),
            inverseJoinColumns = @JoinColumn(name = "participant_id", foreignKey = @ForeignKey(name = "event_participant_participant_id_fk"))
    )
    private Collection<User> participants;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "event_tag",
            joinColumns = @JoinColumn(name = "event_id", foreignKey = @ForeignKey(name = "event_tag_event_id_fk")),
            inverseJoinColumns = @JoinColumn(name = "tag_id", foreignKey = @ForeignKey(name = "event_tag_tag_id_fk"))
    )
    private Collection<Tag> tags;

    public Event() {}

    public Event(User creator, String eventName, String content, LocalDateTime date, double altitude, double latitude, Collection<Tag> tags) {
        this.creator = creator;
        this.eventName = eventName;
        this.content = content;
        this.date = date;
        this.altitude = altitude;
        this.latitude = latitude;
        this.tags = tags;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Collection<Image> getImages() {
        return images;
    }

    public void setImages(Collection<Image> images) {
        this.images = images;
    }

    public Collection<User> getParticipants() {
        return participants;
    }

    public void setParticipants(Collection<User> participants) {
        this.participants = participants;
    }

    public Collection<Tag> getTags() {
        return tags;
    }

    public void setTags(Collection<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", creator=" + creator +
                ", eventName='" + eventName + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                ", altitude=" + altitude +
                ", latitude=" + latitude +
                ", allowed=" + allowed +
                '}';
    }
}
