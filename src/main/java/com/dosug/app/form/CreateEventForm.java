package com.dosug.app.form;

import com.dosug.app.respose.model.ApiErrorCode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Pattern;

public class CreateEventForm {

    public static final int EVENTNAME_MAX_SYMBOLS = 256;

    public static final int TAG_MIN_SYMBOLS = 1;

    public static final int TAG_MAX_SYMBOLS = 256;

    public static final int MIN_LONGITUDE = -180;

    public static final int MAX_LONGITUDE = 180;

    public static final int MIN_LATITUDE = -90;

    public static final int MAX_LATITUDE = 90;

    @ErrorCode(code = ApiErrorCode.INVALID_PLACE_NAME)
    @Size(min = 1, max = 200, message = "name of place should be shorter than 200 characters")
    String placeName;

    @ErrorCode(code = ApiErrorCode.INVALID_EVENT_NAME)
    @NotNull(message = "event name is required")
    @Size(min = 1, max = EVENTNAME_MAX_SYMBOLS, message = "event name length from 1 to 256")
    @javax.validation.constraints.Pattern(regexp = "[a-zA-Zа-яА-Я0-9 _]*", message = "only character, digits, space and underscore allowed in event name")
    private String eventName;

    @ErrorCode(code = ApiErrorCode.INVALID_EVENT_CONTENT)
    @NotNull(message = "content is required")
    private String content;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @ErrorCode(code = ApiErrorCode.INVALID_EVENT_DATE)
    @NotNull(message = "dateTime field is required")
//    @Future
    private LocalDateTime dateTime;

    @ErrorCode(code = ApiErrorCode.INVALID_LONGITUDE)
    @Min(value = MIN_LONGITUDE, message = "longitude is lower than {value}")
    @Max(value = MAX_LONGITUDE, message = "longitude is higher than {value}")
    private double longitude;

    @ErrorCode(code = ApiErrorCode.INVALID_LATITUDE)
    @Min(value = MIN_LATITUDE, message = "latitude is lower than {value}")
    @Max(value = MAX_LATITUDE, message = "latitude is higher than {value}")
    private double latitude;

    @ErrorCode(code = ApiErrorCode.INVALID_EVENT_TAGS)
    @NotNull(message = "tags is required")
    @Size(min = 1, max = 10, message = "from one to ten tag is required")
    private ArrayList<String> tags;

    public CreateEventForm() {
    }

    public CreateEventForm(String eventName, String content, LocalDateTime dateTime, String placeName, double longitude, double latitude, ArrayList<String> tags) {
        this.eventName = eventName;
        this.content = content;
        this.dateTime = dateTime;
        this.placeName = placeName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.tags = tags;
    }

    @ErrorCode(code = ApiErrorCode.INVALID_EVENT_TAG)
    @AssertTrue(message = "wrong tag")
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
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
}
