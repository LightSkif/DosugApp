package com.dosug.app.form;

import com.dosug.app.response.model.ApiErrorCode;
import com.dosug.app.utils.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.dosug.app.utils.Consts.MIN_LONGITUDE;

public class CreateEventForm {

    @ErrorCode(code = ApiErrorCode.INVALID_PLACE_NAME)
    @Size(min = Consts.PLACE_NAME_MIN_SYMBOLS, max = Consts.PLACE_NAME_MAX_SYMBOLS, message = "place_name_length_1_200")
    private String placeName;

    @ErrorCode(code = ApiErrorCode.INVALID_EVENT_NAME)
    @NotNull(message = "event_name_required")
    @Size(min = Consts.EVENT_NAME_MIN_SYMBOLS, max = Consts.EVENT_NAME_MAX_SYMBOLS, message = "event_name_length_1_256")
    @javax.validation.constraints.Pattern(regexp = "[a-zA-Zа-яА-Я0-9 _]*", message = "event_name_latin_russian_digit_space_underscore")
    private String eventName;

    @ErrorCode(code = ApiErrorCode.INVALID_EVENT_CONTENT)
    @NotNull(message = "content_required")
    @Size(min = Consts.CONTENT_MIN_SYMBOLS, max = Consts.CONTENT_MAX_SYMBOLS)
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
    @Max(value = Consts.MAX_LONGITUDE, message = "longitude_higher_180")
    private double longitude;

    @ErrorCode(code = ApiErrorCode.INVALID_LATITUDE)
    @Min(value = Consts.MIN_LATITUDE, message = "latitude_lower_-90")
    @Max(value = Consts.MAX_LATITUDE, message = "latitude_higher_90")
    private double latitude;

    @ErrorCode(code = ApiErrorCode.INVALID_EVENT_TAGS)
    @NotNull(message = "tags_required")
    @Size(min = Consts.TAG_MIN_AMOUNT, max = Consts.TAG_MAX_AMOUNT, message = "tags_length_1_10")
    private ArrayList<String> tags;

    public CreateEventForm() {
    }

    @ErrorCode(code = ApiErrorCode.INVALID_EVENT_TAG)
    @AssertTrue(message = "wrong_tag")
    public boolean isRightSizeTag() {

        if (tags != null) {
            Pattern regexPattern = Pattern.compile("[a-zA-Zа-яА-Я0-9-_]*");
            Optional<String> tagMistake = tags.stream()
                    .filter(s -> ((s.length() > Consts.TAG_MAX_SYMBOLS) ||
                            (s.length() < Consts.TAG_MIN_SYMBOLS) || !regexPattern.matcher(s).matches()))
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
