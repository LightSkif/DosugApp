package com.dosug.app.response.viewmodel.admin;

import com.dosug.app.domain.Tag;
import com.dosug.app.domain.User;
import com.dosug.app.utils.LocalDateSerializer;
import com.dosug.app.utils.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class UserView {

    @JsonProperty
    private long id;

    @JsonProperty
    private String username;

    @JsonProperty
    private String email;

    @JsonProperty
    private String firstName;

    @JsonProperty
    private String lastName;

    @JsonProperty
    private String avatar;

    @JsonProperty
    private String description;

    @JsonProperty
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthDate;

    @JsonProperty
    private String phone;

    @JsonProperty
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createDate;

    @JsonProperty
    private List<String> tags;

    @JsonProperty
    private List<EventPreview> events;

    @JsonProperty
    private List<EventPreview> createdEvents;

    public UserView(User user) {
        id = user.getId();
        username = user.getUsername();
        email = user.getEmail();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        avatar = user.getUsername();
        description = user.getDescription();
        birthDate = user.getBirthDate();
        phone = user.getPhone();
        createDate = user.getCreateDate();

        tags = user.getTags().stream()
                .map(Tag::getTagName)
                .collect(Collectors.toList());

        events = user.getEvents().stream()
                    .map(EventPreview::new)
                    .collect(Collectors.toList());

        createdEvents = user.getCreatedEvents().stream()
                            .distinct()
                            .map(EventPreview::new)
                            .collect(Collectors.toList());
    }
}
