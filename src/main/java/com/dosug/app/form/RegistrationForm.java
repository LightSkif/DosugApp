package com.dosug.app.form;

import com.dosug.app.respose.model.ApiErrorCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Форма для регистрации
 */
public class RegistrationForm {
    private static final int EMAIL_MAX_LENGTH = 256;
    private static final int MAX_PASSWORD_LENGTH = 256;
    private static final int USERNAME_MAX_LENGTH = 256;

    @ErrorCode(code = ApiErrorCode.INVALID_USERNAME)
    @Length(min = 1, max = USERNAME_MAX_LENGTH, message = "username length from 1 to 256")
    @NotNull(message = "username field is required")
    @Pattern(regexp = "[a-zA-Z0-9-_]*", message = "only latin character, digits, underline and hyphen allowed in username")
    private String username;

    @ErrorCode(code = ApiErrorCode.INVALID_EMAIL)
    @NotNull(message = "email field is required")
    @Size(min = 1, max = EMAIL_MAX_LENGTH, message = "email length from 1 to 256")
    @Email(message = "not correct email")
    private String email;

    @ErrorCode(code = ApiErrorCode.INVALID_PASSWORD)
    @NotNull(message = "password field is required")
    @Size(min = 1, max = MAX_PASSWORD_LENGTH, message = "password length from 1 to 256")
    @Pattern(regexp = "[a-zA-Z0-9_]*", message = "only latin character, digits and underscore allowed in password")
    private String password;

    @JsonProperty("passwordConfirmation")
    private String passwordRetry;

    @ErrorCode(code = ApiErrorCode.INVALID_PASSWORD_RETRY)
    @AssertTrue(message = "password and password retry not equals")
    public boolean isRetryEqualsPassword() {
        // NPE check
        if (password != null) {
            return password.equals(passwordRetry);
        }
        return false;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRetry() {
        return passwordRetry;
    }

    public void setPasswordRetry(String passwordRetry) {
        this.passwordRetry = passwordRetry;
    }
}
