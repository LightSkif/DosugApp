package com.dosug.app.form;

import com.dosug.app.respose.model.ApiErrorCode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class CreateEventForm {

    public static final int EVENTNAME_MAX_SYMBOLS = 256;
    public static final int TAG_MAX_SYMBOLS = 256;

    public static final int MIN_ALTITUDE = -90;
    public static final int MAX_ALTITUDE = 90;
    public static final int MIN_LATITUDE = -180;
    public static final int MAX_LATITUDE = 180;

    @ErrorCode(code = ApiErrorCode.INVALID_EVENT_NAME)
    @NotNull(message = "event name is required")
    @Size(min = 1, max = EVENTNAME_MAX_SYMBOLS, message = "event name length from 1 to 256")
    private String eventName;

    @ErrorCode(code = ApiErrorCode.INVALID_EVENT_CONTENT)
    @NotNull(message = "content is required")
    private String content;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @ErrorCode(code = ApiErrorCode.INVALID_EVENT_DATE)
    @NotNull(message = "date field is required")
    //@Future
    private LocalDateTime date;

    @ErrorCode(code = ApiErrorCode.INVALID_ALTITUDE)
    @Min(value = MIN_ALTITUDE, message = "longitude is lower than {value}")
    @Max(value = MAX_ALTITUDE, message = "longitude is higher than {value}")
    private double longitude;

    @ErrorCode(code = ApiErrorCode.INVALID_LATITUDE)
    @Min (value = MIN_LATITUDE, message = "latitude is lower than {value}")
    @Max (value = MAX_LATITUDE, message = "latitude is higher than {value}")
    private double latitude;

    @ErrorCode(code = ApiErrorCode.INVALID_EVENT_TAGS)
    @NotNull(message = "tags is required")
    @Size(min = 1, max = 10, message = "from one to ten tag is required")
    private ArrayList<String> tags;

    @ErrorCode(code = ApiErrorCode.INVALID_EVENT_TAG)
    @AssertTrue(message = "wrong tag")
    public boolean isRightSizeTag() {

        if (tags != null) {
            Pattern regexPattern = Pattern.compile("[a-zA-Z0-9-_]*");
            String tag = tags.stream()
                    .filter(s -> ((s.length() > 256) ||
                            (s.length() < 1) || regexPattern.matcher(s).matches()))
                    .findFirst().toString();
            return tag == null;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
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
