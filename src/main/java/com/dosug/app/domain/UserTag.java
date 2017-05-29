package com.dosug.app.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user_tag")
public class UserTag {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @OneToMany(targetEntity = UserLike.class,
               cascade = CascadeType.REMOVE,
               mappedBy = "ratedUserTag", fetch = FetchType.EAGER)
    private Set<UserLike> likes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Set<UserLike> getLikes() {
        return likes;
    }

    public void setLikes(Set<UserLike> likes) {
        this.likes = likes;
    }

    //    public int getLikeCount() {
//        return likeCount;
//    }
//
//    public void setLikeCount(int likeCount) {
//        this.likeCount = likeCount;
//    }
}
