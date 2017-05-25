package com.dosug.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by radmir on 23.03.17.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "description")
    private String description;

    @Column(name = "birthDate")
    private LocalDate birthDate;

    @Column(name = "phone")
    private String phone;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @OneToMany(targetEntity = AuthToken.class,
            cascade = CascadeType.ALL,
            mappedBy = "user", fetch = FetchType.EAGER)
    private Set<AuthToken> authToken;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_role_users_id_fk")),
            inverseJoinColumns = @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "user_role_roles_id_fk"))
    )
    private Set<Role> roles;

    @OneToMany(targetEntity = Event.class,
            cascade = CascadeType.ALL,
            mappedBy = "creator")
    private List<Event> createdEvents;

    @OneToMany(targetEntity = EventParticipant.class,
            cascade = CascadeType.REMOVE,
            mappedBy = "user", fetch = FetchType.EAGER)
    private Set<EventParticipant> eventLinks;


    @OneToMany(targetEntity = UserLike.class,
            mappedBy = "evaluateUser",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER)
    private Set<UserLike> likes;

    @OneToMany(targetEntity = UserTag.class,
            mappedBy = "user",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER)
    private Set<UserTag> tagLinks;

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Set<AuthToken> getAuthToken() {
        return authToken;
    }

    public void setAuthToken(Set<AuthToken> authToken) {
        this.authToken = authToken;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<Event> getCreatedEvents() {
        return createdEvents;
    }

    public void setCreatedEvents(List<Event> createdEvents) {
        this.createdEvents = createdEvents;
    }

    public Set<EventParticipant> getEventLinks() {
        return eventLinks;
    }

    public void setEventLinks(Set<EventParticipant> eventLinks) {
        this.eventLinks = eventLinks;
    }

    public Set<UserLike> getLikes() {
        return likes;
    }

    public void setLikes(Set<UserLike> likes) {
        this.likes = likes;
    }

    public Set<UserTag> getTagLinks() {
        return tagLinks;
    }

    public void setTagLinks(Set<UserTag> tagLinks) {
        this.tagLinks = tagLinks;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (getUsername() != null ? !getUsername().equals(user.getUsername()) : user.getUsername() != null)
            return false;
        return getEmail() != null ? getEmail().equals(user.getEmail()) : user.getEmail() == null;
    }

    @Override
    public int hashCode() {
        int result = getUsername() != null ? getUsername().hashCode() : 0;
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        return result;
    }
}
