package com.dosug.app.respose.viewmodel;

import com.dosug.app.domain.Event;
import com.dosug.app.utils.LocalDateTimeDeserializer;
import com.dosug.app.utils.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

// Объект для возвращения полного события на клиент.
public class EventPreview {

    @JsonProperty
    String avatar;

    @JsonProperty
    String placeName;

    @JsonProperty
    private Long eventId;

    @JsonProperty
    private String eventName;

    @JsonProperty
    private String content;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime eventDateTime;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createDateTime;

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
        eventDateTime = event.getEventDateTime();
        avatar = event.getAvatar();
        participantsCount = event.getParticipantsLinks().size();
        createDateTime = event.getCreateDate();
        tags = event.getTags().stream().map(s -> s.getTagName()).collect(Collectors.toList());
    }
}
