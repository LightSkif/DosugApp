package com.dosug.app.domain;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * A long time ago in a galaxy far, far away...
 */
@Entity
@Table(name = "events")
@Data
public class Event {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id",
            foreignKey = @ForeignKey(name = "events_creator_id_fk"))
    private User creator;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "content")
    private String content;

    @Column(name = "event_start_date_time")
    private LocalDateTime eventDateTime;

    @Column(name = "event_end_date_time")
    private LocalDateTime endDateTime;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "place_name")
    private String placeName;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "like_count")
    private int likeCount;

    @Column(name = "allowed")
    private Boolean allowed;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @OneToMany(targetEntity = EventParticipant.class,
            cascade = CascadeType.REMOVE,
            mappedBy = "event", fetch = FetchType.EAGER)
    private Set<EventParticipant> participantLinks;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "event_tag",
            joinColumns = @JoinColumn(name = "event_id", foreignKey = @ForeignKey(name = "event_tag_event_id_fk")),
            inverseJoinColumns = @JoinColumn(name = "tag_id", foreignKey = @ForeignKey(name = "event_tag_tag_id_fk"))
    )
    private Set<Tag> tags;


    public Event() {
    }

    public Event(User creator, String eventName, String content, LocalDateTime eventDateTime, String placeName, double longitude, double latitude, Set<Tag> tags) {
        this.creator = creator;
        this.eventName = eventName;
        this.content = content;
        this.eventDateTime = eventDateTime;
        this.placeName = placeName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (creator != null ? !creator.equals(event.creator) : event.creator != null) return false;
        if (eventName != null ? !eventName.equals(event.eventName) : event.eventName != null) return false;
        return eventDateTime != null ? eventDateTime.equals(event.eventDateTime) : event.eventDateTime == null;
    }

    @Override
    public int hashCode() {
        int result = creator != null ? creator.hashCode() : 0;
        result = 31 * result + (eventName != null ? eventName.hashCode() : 0);
        result = 31 * result + (eventDateTime != null ? eventDateTime.hashCode() : 0);
        return result;
    }
}
