package com.dosug.app.respose.viewmodel;

import com.dosug.app.domain.Event;
import com.dosug.app.utils.DurationDeserializer;
import com.dosug.app.utils.DurationSerializer;
import com.dosug.app.utils.LocalDateTimeDeserializer;
import com.dosug.app.utils.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

// Объект для возвращения частичного события на клиент.
public class EventView {

    @JsonProperty
    String placeName;

    @JsonProperty
    private Long eventId;

    @JsonProperty
    private Long creatorId;

    @JsonProperty
    private String eventName;

    @JsonProperty
    private String content;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime eventDateTime;

    @JsonSerialize(using = DurationSerializer.class)
    @JsonDeserialize(using = DurationDeserializer.class)
    private Duration period;

    @JsonProperty
    private double longitude;

    @JsonProperty
    private double latitude;

    @JsonProperty
    private String avatar;

    @JsonProperty
    private List<String> images;

    @JsonProperty
    private List<Long> participants;

    @JsonProperty
    private List<String> tags;

    @JsonProperty
    private boolean isEnded;

    public EventView() {

    }

    public EventView(Event event) {

        eventId = event.getId();
        creatorId = event.getCreator().getId();
        eventName = event.getEventName();
        content = event.getContent();
        eventDateTime = event.getEventDateTime();
        period = Duration.between(event.getEventDateTime(), event.getEndDateTime());
        placeName = event.getPlaceName();
        longitude = event.getLongitude();
        latitude = event.getLatitude();
        avatar = event.getAvatar();

        images = event.getImages().stream().map(s -> s.getImage_source()).collect(Collectors.toList());
        participants = event.getParticipants().stream().map(s -> s.getId()).collect(Collectors.toList());

        // Если событие не завершилось отправляем список тегов с нулевыми оценками.
        isEnded = !LocalDateTime.now().isBefore(event.getEndDateTime());

        tags = event.getTags().stream().map(s -> s.getTagName()).collect(Collectors.toList());
    }
}
