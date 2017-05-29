package com.dosug.app.form;

import com.dosug.app.response.model.ApiErrorCode;
import com.dosug.app.utils.Consts;
import com.dosug.app.utils.ISOLocalDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class UpdateUserForm {

    @ErrorCode(code = ApiErrorCode.INVALID_USER_ID)
    @Min(value = Consts.MIN_ID, message = "id is lower than {value}")
    @NotNull(message = "userId field is required")
    private long userId;

    @ErrorCode(code = ApiErrorCode.INVALID_USER_FIRST_NAME)
    @Size(min = Consts.FIRST_NAME_MIN_SYMBOLS, max = Consts.FIRST_NAME_MAX_SYMBOLS, message = "firstName should be shorter than 100 characters")
    @Pattern(regexp = "[a-zA-Zа-яА-Я- ]*", message = "only character, hypen and space allowed in firstName")
    private String firstName;

    @ErrorCode(code = ApiErrorCode.INVALID_USER_LAST_NAME)
    @Size(min = Consts.LAST_NAME_MIN_SYMBOLS, max = Consts.LAST_NAME_MAX_SYMBOLS, message = "firstName should be shorter than 100 characters")
    @Pattern(regexp = "[a-zA-Zа-яА-Я- ]*", message = "only character, hypen and space allowed in firstName")
    private String lastName;

    @ErrorCode(code = ApiErrorCode.INVALID_USER_DESCIPTION)
    @NotNull(message = "description field is required")
    @Size(min = Consts.DESCRIPTION_MIN_SYMBOLS, max = Consts.DESCRIPTION_MAX_SYMBOLS, message = "description should be shorter than 1000 characters")
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
