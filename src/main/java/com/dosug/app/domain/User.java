package com.dosug.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by radmir on 23.03.17.
 */
@Data
@Entity
@Table(name = "users")
@NamedQueries({
    @NamedQuery(name = "User.simpleSearch",
                query = "FROM User as u " +
                        "WHERE u.username LIKE CONCAT('%', :part, '%') " +
                        "or u.email LIKE CONCAT('%', :part, '%')"),
    @NamedQuery(name = "User.simpleSearchCount",
                query = "SELECT COUNT(*) FROM User as u " +
                        "WHERE u.username LIKE CONCAT('%', :part, '%') " +
                        "or u.email LIKE CONCAT('%', :part, '%')")
})
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

    @OneToMany(targetEntity = UserTag.class,
            mappedBy = "user",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER)
    private Set<UserTag> tagLinks;

    @Column(name = "is_banned")
    @Getter
    private boolean banned;

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
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
