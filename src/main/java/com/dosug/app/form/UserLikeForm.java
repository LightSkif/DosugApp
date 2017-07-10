package com.dosug.app.form;

import com.dosug.app.response.model.ApiErrorCode;
import com.dosug.app.utils.Consts;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UserLikeForm {

    @ErrorCode(code = ApiErrorCode.INVALID_USER_ID)
    @Min(value = Consts.MIN_ID, message = "id_lower_1")
    @NotNull(message = "rated_user_id_required")
    private long ratedUserId;

    @ErrorCode(code = ApiErrorCode.INVALID_EVENT_ID)
    @Min(value = Consts.MIN_ID, message = "id_lower_1")
    @NotNull(message = "event_id_required")
    private long eventId;

    @ErrorCode(code = ApiErrorCode.INVALID_TAG_ID)
    @Min(value = Consts.MIN_ID, message = "id_lower_1")
    @NotNull(message = "tag_id_required")
    private long tagId;
}
