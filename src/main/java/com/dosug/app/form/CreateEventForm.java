package com.dosug.app.form;

import com.dosug.app.respose.model.ApiErrorCode;
import com.dosug.app.utils.DurationDeserializer;
import com.dosug.app.utils.DurationSerializer;
import com.dosug.app.utils.LocalDateTimeDeserializer;
import com.dosug.app.utils.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Pattern;

public class CreateEventForm {

    public static final int EVENTNAME_MAX_SYMBOLS = 256;

    public static final int TAG_MIN_SYMBOLS = 1;

    public static final int TAG_MAX_SYMBOLS = 256;

    public static final int TAG_MIN_AMOUNT = 1;

    public static final int TAG_MAX_AMOUNT = 10;

    public static final int MIN_LONGITUDE = -180;

    public static final int MAX_LONGITUDE = 180;

    public static final int MIN_LATITUDE = -90;

    public static final int MAX_LATITUDE = 90;

    @ErrorCode(code = ApiErrorCode.INVALID_PLACE_NAME)
    @Size(min = 1, max = 200, message = "place_name_length_1_200")
    String placeName;

    @ErrorCode(code = ApiErrorCode.INVALID_EVENT_NAME)
    @NotNull(message = "event_name_required")
    @Size(min = 1, max = EVENTNAME_MAX_SYMBOLS, message = "event_name_length_1_256")
    @javax.validation.constraints.Pattern(regexp = "[a-zA-Zа-яА-Я0-9 _]*", message = "event_name_latin_russian_digit_space_underscore")
    private String eventName;

    @ErrorCode(code = ApiErrorCode.INVALID_EVENT_CONTENT)
    @NotNull(message = "content_required")
    private String content;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @ErrorCode(code = ApiErrorCode.INVALID_EVENT_DATE)
    @NotNull(message = "event_datetime_required")
//    @Future
    private LocalDateTime eventDateTime;

    @JsonSerialize(using = DurationSerializer.class)
    @JsonDeserialize(using = DurationDeserializer.class)
    @ErrorCode(code = ApiErrorCode.INVALID_PERIOD)
    @NotNull(message = "event_period_required")
    private Duration period;

    @ErrorCode(code = ApiErrorCode.INVALID_LONGITUDE)
    @Min(value = MIN_LONGITUDE, message = "longitude_lower_180")
    @Max(value = MAX_LONGITUDE, message = "longitude_higher_180")
    private double longitude;

    @ErrorCode(code = ApiErrorCode.INVALID_LATITUDE)
    @Min(value = MIN_LATITUDE, message = "latitude_lower_-90")
    @Max(value = MAX_LATITUDE, message = "latitude_higher_90")
    private double latitude;

    @ErrorCode(code = ApiErrorCode.INVALID_EVENT_TAGS)
    @NotNull(message = "tags_required")
    @Size(min = TAG_MIN_AMOUNT, max = TAG_MAX_AMOUNT, message = "tags_length_1_10")
    private ArrayList<String> tags;

    public CreateEventForm() {
    }

    @ErrorCode(code = ApiErrorCode.INVALID_EVENT_TAG)
    @AssertTrue(message = "wrong_tag")
    public boolean isRightSizeTag() {

        if (tags != null) {
            Pattern regexPattern = Pattern.compile("[a-zA-Zа-яА-Я0-9-_]*");
            Optional<String> tagMistake = tags.stream()
                    .filter(s -> ((s.length() > TAG_MAX_SYMBOLS) ||
                            (s.length() < TAG_MIN_SYMBOLS) || !regexPattern.matcher(s).matches()))
                    .findFirst();

            // В случае если ни одной ошибки не найдено, проверка завершена успешно.
            return tagMistake.equals(Optional.empty());
        }

        return true;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(LocalDateTime eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }


    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Duration getPeriod() {
        return period;
    }

    public void setPeriod(Duration period) {
        this.period = period;
    }
}
