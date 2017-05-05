package com.dosug.app.domain;

import com.dosug.app.form.LocalDateTimeDeserializer;
import com.dosug.app.form.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * A long time ago in a galaxy far, far away...
 */
@Entity
@Table(name = "events")
public class Event {

    @Column(name = "placeName")
    String placeName;
    @Id @GeneratedValue
    @Column(name = "id")
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id")
    private User creator;
    @Column(name = "event_name")
    private String eventName;
    @Column(name = "content")
    private String content;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "event_date")
    private LocalDateTime date;
    @Column(name = "longitude")
    private double longitude;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "allowed")
    private boolean allowed;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @OneToMany(targetEntity = Image.class,
            cascade = CascadeType.ALL,
            mappedBy = "event",
            fetch = FetchType.EAGER)
    private List<Image> images;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "event_participant",
            joinColumns = @JoinColumn(name = "event_id", foreignKey = @ForeignKey(name = "event_participant_event_id_fk")),
            inverseJoinColumns = @JoinColumn(name = "participant_id", foreignKey = @ForeignKey(name = "event_participant_participant_id_fk"))
    )
    private Set<User> participants;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "event_tag",
            joinColumns = @JoinColumn(name = "event_id", foreignKey = @ForeignKey(name = "event_tag_event_id_fk")),
            inverseJoinColumns = @JoinColumn(name = "tag_id", foreignKey = @ForeignKey(name = "event_tag_tag_id_fk"))
    )
    private Set<Tag> tags;

    public Event() {}

    public Event(User creator, String eventName, String content, LocalDateTime date, String placeName, double longitude, double latitude, Set<Tag> tags) {
        this.creator = creator;
        this.eventName = eventName;
        this.content = content;
        this.date = date;
        this.placeName = placeName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.tags = tags;
    }

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

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
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

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<User> participants) {
        this.participants = participants;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
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
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", createDate=" + createDate +
                ", allowed=" + allowed +
                '}';
    }
}
