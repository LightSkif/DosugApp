package com.dosug.app.form;

import com.dosug.app.response.model.ApiErrorCode;
import com.dosug.app.utils.Consts;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UpdateUserPasswordForm {


    @ErrorCode(code = ApiErrorCode.INVALID_PASSWORD)
    @NotNull(message = "password_required")
    @Size(min = Consts.PASSWORD_MIN_SYMBOLS, max = Consts.PASSWORD_MAX_SYMBOLS, message = "password_length_8_256")
    @Pattern(regexp = "[a-zA-Z0-9_]*", message = "password_allow_latinchar_digits_underscore")
    private String oldPassword;


    @ErrorCode(code = ApiErrorCode.INVALID_NEW_PASSWORD)
    @NotNull(message = "new_password_required")
    @Size(min = Consts.PASSWORD_MIN_SYMBOLS, max = Consts.PASSWORD_MAX_SYMBOLS, message = "new_password_length_8_256")
    @Pattern(regexp = "[a-zA-Z0-9_]*", message = "new_password_allow_latinchar_digits_underscore")
    private String newPassword;

    @JsonProperty("passwordConfirmation")
    private String newPasswordRetry;

    @ErrorCode(code = ApiErrorCode.INVALID_NEW_PASSWORD_RETRY)
    @AssertTrue(message = "new_password_retry_not_equals")
    public boolean isRetryEqualsPassword() {
        // NPE check
        if (newPassword != null) {
            return newPassword.equals(newPasswordRetry);
        }
        return false;
    }
}
