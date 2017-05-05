package com.dosug.app.respose.viewmodel;

import com.dosug.app.domain.Event;
import com.dosug.app.form.LocalDateTimeDeserializer;
import com.dosug.app.form.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

// Объект для возвращения полного события на клиент.

public class EventPreview {

    @JsonProperty
    String placeName;

    @JsonProperty
    String avatar;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateTime;

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
        dateTime = event.getDate();
        avatar = event.getAvatar();
        participantsCount = event.getParticipants().size();
        tags = event.getTags().stream().map(s -> s.getTag()).collect(Collectors.toList());
    }
}
