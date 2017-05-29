package com.dosug.app.response.viewmodel.admin;


import com.dosug.app.domain.Event;
import com.dosug.app.utils.LocalDateTimeDeserializer;
import com.dosug.app.utils.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;

public class EventPreview {

    @JsonProperty
    String placeName;

    @JsonProperty
    private Long id;

    @JsonProperty
    private String name;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateTime;

    @JsonProperty
    private Boolean allowed;

    public EventPreview(Event event) {

        id = event.getId();
        name = event.getEventName();
        placeName = event.getPlaceName();
        dateTime = event.getEventDateTime();
        allowed = event.isAllowed();
    }
}
