package com.dosug.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

/**
 * Created by radmir on 23.03.17.
 */
@Entity
@Table(name = "users")
public class User {

    @Id @GeneratedValue
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
        mappedBy = "user")
    private Set<AuthToken> authToken;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_role_users_id_fk")),
            inverseJoinColumns = @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "user_role_roles_id_fk"))
    )
    private Collection<Role> roles;

    @OneToMany(targetEntity = Event.class,
            cascade = CascadeType.ALL,
            mappedBy = "creator")
    private Collection<Event> createdEvents;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "event_participant",
            joinColumns = @JoinColumn(name = "participant_id", foreignKey = @ForeignKey(name = "event_participant_participant_id_fk")),
            inverseJoinColumns = @JoinColumn(name = "event_id", foreignKey = @ForeignKey(name = "event_participant_event_id_fk"))
    )
    private Collection<Event> events;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_tag",
            joinColumns = @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_tag_user_id_fk")),
            inverseJoinColumns = @JoinColumn(name = "tag_id", foreignKey = @ForeignKey(name = "user_tag_tag_id_fk"))
    )
    private Collection<Tag> tags;

    public User() {}

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

    public Collection<Event> getCreatedEvents() {
        return createdEvents;
    }

    public void setCreatedEvents(Collection<Event> createdEvents) {
        this.createdEvents = createdEvents;
    }

    public Collection<Event> getEvents() {
        return events;
    }

    public void setEvents(Collection<Event> events) {
        this.events = events;
    }

    public Collection<Tag> getTags() {
        return tags;
    }

    public void setTags(Collection<Tag> tags) {
        this.tags = tags;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", description='" + description + '\'' +
                ", birthDate=" + birthDate +
                ", phone='" + phone + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
