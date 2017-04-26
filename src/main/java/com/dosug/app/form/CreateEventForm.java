package com.dosug.app.form;

import com.dosug.app.respose.model.ApiErrorCode;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

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

    @ErrorCode(code = ApiErrorCode.INVALID_EVENT_DATE)
    @NotNull(message = "date field is required")
    @Future
    private LocalDateTime date;

    @ErrorCode(code = ApiErrorCode.INVALID_ALTITUDE)
    @Min (value = MIN_ALTITUDE, message = "altitude is lower than {value}")
    @Max (value = MAX_ALTITUDE, message = "altitude is higher than {value}")
    private double altitude;

    @ErrorCode(code = ApiErrorCode.INVALID_LATITUDE)
    @Min (value = MIN_LATITUDE, message = "latitude is lower than {value}")
    @Max (value = MAX_LATITUDE, message = "latitude is higher than {value}")
    private double latitude;

    @ErrorCode(code = ApiErrorCode.INVALID_EVENT_TAGS)
    @NotNull(message = "tags is required")
    private ArrayList<String> tags;

    @ErrorCode(code = ApiErrorCode.INVALID_EVENT_TAG)
    @AssertTrue(message = "wrong tag")
    public boolean isRightSizeTag() {

        String tag = tags.stream().filter(s -> ((s.length() > 256) || (s.length() < 1))).findFirst().toString();

        return tag == null ? true : false;
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

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
