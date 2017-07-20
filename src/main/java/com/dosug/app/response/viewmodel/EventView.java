package com.dosug.app.response.viewmodel;

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

/**
 * Объект для возвращения частичного события на клиент.
 */
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
    private List<Long> participants;

    @JsonProperty
    private List<String> tags;

    @JsonProperty
    private boolean isEnded;

    @JsonProperty
    private boolean isLiked;

    @JsonProperty
    private int likeCount;

    public EventView() {

    }

    public EventView(Event event, boolean isLiked) {

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
        likeCount = event.getLikeCount();

        participants = event.getParticipantLinks().stream()
                .map(s -> s.getUser().getId())
                .collect(Collectors.toList());

        tags = event.getTags().stream().map(s -> s.getTagName()).collect(Collectors.toList());

        this.isLiked = isLiked;

        // Проверяем завершилось ли событие и сохраняем как флаг для отправки на клиент.
        isEnded = LocalDateTime.now().isAfter(event.getEndDateTime());
    }
}
