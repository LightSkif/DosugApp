package com.dosug.app.form;

import com.dosug.app.respose.model.ApiErrorCode;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UpdateUserPasswordForm {

    private static final int PASSWORD_MIN_SYMBOLS = 1;
    private static final int PASSWORD_MAX_SYMBOLS = 255;


    @ErrorCode(code = ApiErrorCode.INVALID_PASSWORD)
    @NotNull(message = "password field is required")
    @Size(min = PASSWORD_MIN_SYMBOLS, max = PASSWORD_MAX_SYMBOLS, message = "password length from 1 to 256")
    @Pattern(regexp = "[a-zA-Z0-9_]*", message = "only latin character, digits and underscore allowed in password")
    private String oldPassword;


    @ErrorCode(code = ApiErrorCode.INVALID_NEW_PASSWORD)
    @NotNull(message = "password field is required")
    @Size(min = PASSWORD_MIN_SYMBOLS, max = PASSWORD_MAX_SYMBOLS, message = "password length from 1 to 256")
    @Pattern(regexp = "[a-zA-Z0-9_]*", message = "only latin character, digits and underscore allowed in password")
    private String newPassword;

    @JsonProperty("passwordConfirmation")
    private String newPasswordRetry;

    @ErrorCode(code = ApiErrorCode.INVALID_NEW_PASSWORD_RETRY)
    @AssertTrue(message = "new password and new password retry not equals")
    public boolean isRetryEqualsPassword() {
        // NPE check
        if (newPassword != null) {
            return newPassword.equals(newPasswordRetry);
        }
        return false;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
