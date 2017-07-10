package com.dosug.app.form;

import com.dosug.app.response.model.ApiErrorCode;
import com.dosug.app.utils.Consts;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UpdateEventForm extends CreateEventForm {

    @ErrorCode(code = ApiErrorCode.INVALID_EVENT_ID)
    @Min(value = Consts.MIN_ID, message = "id_lower_1")
    @NotNull(message = "event_id_required")
    private long eventId;
}
