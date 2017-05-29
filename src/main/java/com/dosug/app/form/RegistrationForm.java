package com.dosug.app.form;

import com.dosug.app.response.model.ApiErrorCode;
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
    @Length(min = 1, max = USERNAME_MAX_LENGTH, message = "username_length_1_256")
    @NotNull(message = "username_required")
    @Pattern(regexp = "[a-zA-Z0-9-_]*", message = "username_allow_latinchar_digits_hyphen_underscore")
    private String username;

    @ErrorCode(code = ApiErrorCode.INVALID_EMAIL)
    @NotNull(message = "email_required")
    @Size(min = 1, max = EMAIL_MAX_LENGTH, message = "email_length_1_256")
    @Email(message = "email_not_correct")
    private String email;

    @ErrorCode(code = ApiErrorCode.INVALID_PASSWORD)
    @NotNull(message = "password_required")
    @Size(min = 1, max = MAX_PASSWORD_LENGTH, message = "password_length_1_256")
    @Pattern(regexp = "[0-9a-zA-Z_]*", message = "password_allow_latinchar_digits_underscore")
    private String password;

    @JsonProperty("passwordConfirmation")
    private String passwordRetry;

    @ErrorCode(code = ApiErrorCode.INVALID_PASSWORD_RETRY)
    @AssertTrue(message = "password_retry_not_equals")
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
