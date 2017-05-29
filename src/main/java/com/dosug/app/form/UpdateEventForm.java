package com.dosug.app.form;

import com.dosug.app.response.model.ApiErrorCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UpdateEventForm extends CreateEventForm {

    public static final int MIN_ID = 1;

    @ErrorCode(code = ApiErrorCode.INVALID_EVENT_ID)
    @Min(value = MIN_ID, message = "id_lower_1")
    @NotNull(message = "event_id_required")
    private long eventId;

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }
}
