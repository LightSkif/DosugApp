package com.dosug.app.form;

import com.dosug.app.respose.model.ApiErrorCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by radmir on 24.03.17.
 */


public class AuthenticationForm {

    public static final int USERNAME_MAX_SYMBOLS = 256;
    public static final int PASSWORD_MAX_SYMBOLS = 256;

    @ErrorCode(code = ApiErrorCode.INVALID_USERNAME)
    @NotNull(message = "username field is required")
    @Size(min = 1, max = USERNAME_MAX_SYMBOLS, message = "username length from 1 to 256")
    @Pattern(regexp = "[a-zA-Z0-9-_]*", message = "only latin character, digits, underline and hyphen allowed in username")
    private String username;


    @ErrorCode(code = ApiErrorCode.INVALID_PASSWORD)
    @NotNull(message = "password field is required")
    @Size(min = 1, max = PASSWORD_MAX_SYMBOLS, message = "password length from 1 to 256")
    @Pattern(regexp = "[0-9a-zA-Z_]*", message = "only latin character, digits and hyphen allowed in password")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
