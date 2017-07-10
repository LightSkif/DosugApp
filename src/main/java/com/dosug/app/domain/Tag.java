package com.dosug.app.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tags")
@Data
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
