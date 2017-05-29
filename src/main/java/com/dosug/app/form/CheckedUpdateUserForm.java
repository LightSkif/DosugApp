package com.dosug.app.form;

import com.dosug.app.response.model.ApiErrorCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


/**
 * Базовая форма для изменений полей, требующих проверки пароля.
 */
public class CheckedUpdateUserForm {

    public static final int MIN_ID = 1;

    public static final int PASSWORD_MIN_SYMBOLS = 1;

    public static final int PASSWORD_MAX_SYMBOLS = 256;

    @ErrorCode(code = ApiErrorCode.INVALID_USER_ID)
    @Min(value = MIN_ID, message = "id is lower than {value}")
    @NotNull(message = "userId field is required")
    private long userId;

    @ErrorCode(code = ApiErrorCode.INVALID_PASSWORD)
    @NotNull(message = "password field is required")
    @Size(min = 1, max = PASSWORD_MAX_SYMBOLS, message = "password length from 1 to 256")
    @Pattern(regexp = "[0-9a-zA-Z_]*", message = "only latin character, digits and hyphen allowed in password")
    private String password;
}
