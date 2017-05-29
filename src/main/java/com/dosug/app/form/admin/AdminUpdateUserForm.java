package com.dosug.app.form.admin;

import com.dosug.app.form.ErrorCode;
import com.dosug.app.respose.model.ApiErrorCode;
import com.dosug.app.utils.LocalDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Created by radmir on 23.05.17.
 */
public class AdminUpdateUserForm {

    private static final int EMAIL_MAX_LENGTH = 256;

    private static final int USERNAME_MAX_LENGTH = 256;

    private static final int FIRST_NAME_MAX_LENGTH = 60;

    private static final int LAST_NAME_MAX_LENGTH = 60;

    private static final int DESCRIPTION_MAX_LENGTH = 1000;

    private static final int PHONE_MAX_LENGTH = 15;
    private static final int MAX_PASSWORD_LENGTH = 256;

    @ErrorCode(code = ApiErrorCode.INVALID_USERNAME)
    @Length(min = 1, max = USERNAME_MAX_LENGTH, message = "username_length_1_256")
    @NotNull(message = "username_required")
    @Pattern(regexp = "[a-zA-Z0-9-_]*", message = "username_allow_latinchar_digits_hyphen_underscore")
    private String username;

    @ErrorCode(code = ApiErrorCode.INVALID_EMAIL)
    @NotNull(message = "email_required")
    @Size(min = 1, max = EMAIL_MAX_LENGTH)
    @Email(message = "email_not_correct")
    private String email;

    @ErrorCode(code = ApiErrorCode.INVALID_PASSWORD)
    @Size(min = 1, max = MAX_PASSWORD_LENGTH, message = "password_length_1_256")
    @Pattern(regexp = "[0-9a-zA-Z_]*", message = "password_allow_latinchar_digits_underscore")
    private String password;


    @ErrorCode(code = ApiErrorCode.INVALID_USER_FIRST_NAME)
    @Size(min = 0, max = FIRST_NAME_MAX_LENGTH, message = "first_name_length_0_60")
    @Pattern(regexp = "[a-zA-Zа-яА-Я- ]*", message = "first_name_not_allowed_symbols")
    private String firstName;

    @ErrorCode(code = ApiErrorCode.INVALID_USER_LAST_NAME)
    @Size(min = 0, max = LAST_NAME_MAX_LENGTH, message = "last_name_length_0_60")
    @Pattern(regexp = "[a-zA-Zа-яА-Я- ]*", message = "last_name_not_allowed_symbols")
    private String lastName;

    @ErrorCode(code = ApiErrorCode.INVALID_USER_DESCIPTION)
    @Size(min = 0, max = DESCRIPTION_MAX_LENGTH)
    private String description;

    @ErrorCode(code = ApiErrorCode.INVALID_USER_BIRTH_DATE)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    //@Past
    private LocalDate birthDate;

    @ErrorCode(code = ApiErrorCode.INVALID_PHONE)
    @Size(min = 0, max = PHONE_MAX_LENGTH)
    @Pattern(regexp = "[0-9]*", message = "phone_allow_digits")
    private String phone;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
