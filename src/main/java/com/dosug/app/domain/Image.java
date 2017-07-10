package com.dosug.app.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "images")
@Data
public class Image {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "image_source")
    private String image_source;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id",
            foreignKey = @ForeignKey(name = "images_event_id_fk"))
    private Event event;
}
