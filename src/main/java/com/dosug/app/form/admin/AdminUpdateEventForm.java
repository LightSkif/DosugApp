package com.dosug.app.form.admin;

import com.dosug.app.form.ErrorCode;
import com.dosug.app.response.model.ApiErrorCode;
import com.dosug.app.utils.Consts;
import com.dosug.app.utils.LocalDateTimeDeserializer;
import com.dosug.app.utils.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class AdminUpdateEventForm {

    @ErrorCode(code = ApiErrorCode.INVALID_PLACE_NAME)
    @Size(min = 1, max = 200, message = "place_name_length_1_200")
    String placeName;

    @ErrorCode(code = ApiErrorCode.INVALID_EVENT_NAME)
    @NotNull(message = "event_name_required")
    @Size(min = Consts.EVENT_NAME_MIN_SYMBOLS, max = Consts.EVENT_NAME_MAX_SYMBOLS, message = "event_name_length_1_256")
    @javax.validation.constraints.Pattern(regexp = "[a-zA-Zа-яА-Я0-9 _]*", message = "event_name_latin_russian_digit_space_underscore")
    private String eventName;

    @ErrorCode(code = ApiErrorCode.INVALID_EVENT_CONTENT)
    @NotNull(message = "content_required")
    private String content;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @ErrorCode(code = ApiErrorCode.INVALID_EVENT_DATE)
    @NotNull(message = "event_datetime_required")
    private LocalDateTime dateTime;

    @ErrorCode(code = ApiErrorCode.INVALID_LONGITUDE)
    @Min(value = Consts.MIN_LONGITUDE, message = "longitude_lower_180")
    @Max(value = Consts.MAX_LONGITUDE, message = "longitude_higher_180")
    private double longitude;

    @ErrorCode(code = ApiErrorCode.INVALID_LATITUDE)
    @Min(value = Consts.MIN_LATITUDE, message = "latitude_lower_-90")
    @Max(value = Consts.MAX_LATITUDE, message = "latitude_higher_90")
    private double latitude;

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
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
