package com.dosug.app.response.viewmodel.admin;


import com.dosug.app.domain.Event;
import com.dosug.app.domain.EventParticipant;
import com.dosug.app.domain.User;
import com.dosug.app.utils.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class EventView {

    @JsonProperty
    private long id;

    @JsonSerialize()
    private EventUserPreview creator;

    @JsonProperty
    private String name;

    @JsonProperty
    private String content;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime eventDateTime;

    @JsonProperty
    private double longitude;

    @JsonProperty
    private double latitude;

    @JsonProperty
    private String placeName;

    @JsonProperty
    private Boolean allowed;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createDate;

    @JsonProperty
    private List<EventUserPreview> participants;

    private List<TagView> tags;

    public EventView(Event event) {
        id = event.getId();
        creator = new EventUserPreview(event.getCreator());
        name = event.getEventName();
        content = event.getContent();
        eventDateTime = event.getEventDateTime();
        longitude = event.getLongitude();
        latitude = event.getLatitude();
        placeName = event.getPlaceName();
        allowed = event.isAllowed();
        createDate = event.getCreateDate();

        participants = event.getParticipantLinks().stream()
                .map(EventParticipant::getUser)
                        .map(EventUserPreview::new)
                        .collect(Collectors.toList());

        tags = event.getTags().stream()
                .map(TagView::new)
                .collect(Collectors.toList());
    }

    private class EventUserPreview {

        @JsonProperty
        private long id;

        @JsonProperty
        private String username;

        public EventUserPreview(User user) {
            id = user.getId();
            username = user.getUsername();
        }
    }

}
