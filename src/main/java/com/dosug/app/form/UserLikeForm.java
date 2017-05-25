package com.dosug.app.form;

import com.dosug.app.respose.model.ApiErrorCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


public class UserLikeForm {

    public static final int MIN_ID = 1;

    @ErrorCode(code = ApiErrorCode.INVALID_USER_ID)
    @Min(value = MIN_ID, message = "id_lower_1")
    @NotNull(message = "rated user id required")
    private long ratedUserId;

    @ErrorCode(code = ApiErrorCode.INVALID_EVENT_ID)
    @Min(value = MIN_ID, message = "id_lower_1")
    @NotNull(message = "event_id_required")
    private long eventId;

    @ErrorCode(code = ApiErrorCode.INVALID_TAG_ID)
    @Min(value = MIN_ID, message = "id_lower_1")
    @NotNull(message = "tag id required")
    private long tagId;

    public long getRatedUserId() {
        return ratedUserId;
    }

    public void setRatedUserId(long ratedUserId) {
        this.ratedUserId = ratedUserId;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }
}
