package com.dosug.app.form;

import com.dosug.app.respose.model.ApiErrorCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UpdateEventForm extends CreateEventForm {

    public static final int MIN_ID = 1;

    @ErrorCode(code = ApiErrorCode.INVALID_EVENT_ID)
    @Min(value = MIN_ID, message = "id is lower than {value}")
    @NotNull(message = "eventId field is required")
    private long eventId;

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }
}
