package com.dosug.app.form;

import com.dosug.app.response.model.ApiErrorCode;
import com.dosug.app.utils.Consts;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by radmir on 24.03.17.
 */
@Data
public class AuthenticationForm {

    @ErrorCode(code = ApiErrorCode.INVALID_USERNAME)
    @NotNull(message = "username_required")
    @Size(min = Consts.USERNAME_MIN_LENGTH, max = Consts.USERNAME_MAX_LENGTH, message = "username_length_1_256")
    @Pattern(regexp = "[a-zA-Z0-9-_]*", message = "username_allow_latinchar_digits_hyphen_underscore")
    private String username;


    @ErrorCode(code = ApiErrorCode.INVALID_PASSWORD)
    @NotNull(message = "password_required")
    @Size(min = Consts.PASSWORD_MIN_SYMBOLS, max = Consts.PASSWORD_MAX_SYMBOLS, message = "password_length_8_256")
    @Pattern(regexp = "[0-9a-zA-Z_]*", message = "password_allow_latinchar_digits_underscore")
    private String password;
}
