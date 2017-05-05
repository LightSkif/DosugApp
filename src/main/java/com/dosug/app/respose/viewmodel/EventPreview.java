package com.dosug.app.respose.viewmodel;

import com.dosug.app.domain.Event;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

// Объект для возвращения полного события на клиент.

public class EventPreview {

    @JsonProperty
    String placeName;
    @JsonProperty
    String avatar;
    @JsonProperty
    private Long eventId;
    @JsonProperty
    private String eventName;
    @JsonProperty
    private String content;
    @JsonProperty
    private int participantsCount;

    @JsonProperty
    private List<String> tags;

    public EventPreview() {

    }

    public EventPreview(Event event) {

        eventId = event.getId();
        eventName = event.getEventName();
        content = event.getContent();
        placeName = event.getPlaceName();
        avatar = event.getAvatar();
        participantsCount = event.getParticipants().size();
        tags = event.getTags().stream().map(s -> s.getTag()).collect(Collectors.toList());
    }
}
