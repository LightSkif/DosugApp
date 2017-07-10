package com.dosug.app.domain;


import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

/**
 * A long time ago in a galaxy far, far away...
 */
@Entity
@Table(name = "event_participant")
@Data
public class EventParticipant {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "participant_id",
            foreignKey = @ForeignKey(name = "event_participant_participant_id_fk"))
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id",
            foreignKey = @ForeignKey(name = "event_participant_event_id_fk"))
    private Event event;

    @Column(name = "liked")
    @Getter
    private boolean liked;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventParticipant that = (EventParticipant) o;

        if (getUser() != null ? !getUser().equals(that.getUser()) : that.getUser() != null) return false;
        return getEvent() != null ? getEvent().equals(that.getEvent()) : that.getEvent() == null;
    }

    @Override
    public int hashCode() {
        int result = getUser() != null ? getUser().hashCode() : 0;
        result = 31 * result + (getEvent() != null ? getEvent().hashCode() : 0);
        return result;
    }
}
