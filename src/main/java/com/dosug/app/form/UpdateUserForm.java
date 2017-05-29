package com.dosug.app.form;

import com.dosug.app.response.model.ApiErrorCode;
import com.dosug.app.utils.ISOLocalDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class UpdateUserForm {

    public static final int MIN_ID = 1;

    public static final int FIRSTNAME_MIN_SYMBOLS = 1;

    public static final int FIRSTNAME_MAX_SYMBOLS = 128;

    public static final int LASTNAME_MIN_SYMBOLS = 1;

    public static final int LASTNAME_MAX_SYMBOLS = 128;

    public static final int DESCRIPTION_MIN_SYMBOLS = 1;

    public static final int DESCRIPTION_MAX_SYMBOLS = 1024;

    @ErrorCode(code = ApiErrorCode.INVALID_USER_ID)
    @Min(value = MIN_ID, message = "id is lower than {value}")
    @NotNull(message = "userId field is required")
    private long userId;

    @ErrorCode(code = ApiErrorCode.INVALID_FIRSTNAME)
    @Size(min = FIRSTNAME_MIN_SYMBOLS, max = FIRSTNAME_MAX_SYMBOLS, message = "firstName should be shorter than 100 characters")
    @Pattern(regexp = "[a-zA-Zа-яА-Я- ]*", message = "only character, hypen and space allowed in firstName")
    private String firstName;

    @ErrorCode(code = ApiErrorCode.INVALID_LASTNAME)
    @Size(min = LASTNAME_MIN_SYMBOLS, max = LASTNAME_MAX_SYMBOLS, message = "firstName should be shorter than 100 characters")
    @Pattern(regexp = "[a-zA-Zа-яА-Я- ]*", message = "only character, hypen and space allowed in firstName")
    private String lastName;

    @ErrorCode(code = ApiErrorCode.INVALID_DESCRIPTION)
    @NotNull(message = "description field is required")
    @Size(min = DESCRIPTION_MIN_SYMBOLS, max = DESCRIPTION_MAX_SYMBOLS, message = "description should be shorter than 1000 characters")
    private String description;

    @JsonDeserialize(using = ISOLocalDateDeserializer.class)
    private LocalDate birthDate;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

}
