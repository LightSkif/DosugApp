package com.dosug.app.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user_tag")
@Data
public class UserTag {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",
            foreignKey = @ForeignKey(name = "user_tag_user_id_fk"))
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tag_id",
            foreignKey = @ForeignKey(name = "user_tag_tag_id_fk"))
    private Tag tag;

    @OneToMany(targetEntity = UserLike.class,
               cascade = CascadeType.REMOVE,
               mappedBy = "ratedUserTag", fetch = FetchType.EAGER)
    private Set<UserLike> likes;
}
