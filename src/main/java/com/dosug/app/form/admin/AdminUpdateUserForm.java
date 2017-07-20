package com.dosug.app.form.admin;

import com.dosug.app.form.ErrorCode;
import com.dosug.app.response.model.ApiErrorCode;
import com.dosug.app.utils.Consts;
import com.dosug.app.utils.ISOLocalDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Created by radmir on 23.05.17.
 */
@Data
public class AdminUpdateUserForm {

    @ErrorCode(code = ApiErrorCode.INVALID_USERNAME)
    @Length(min = Consts.USERNAME_MIN_LENGTH, max = Consts.USERNAME_MAX_LENGTH, message = "username_length_1_256")
    @NotNull(message = "username_required")
    @Pattern(regexp = "[a-zA-Z0-9-_]*", message = "username_allow_latinchar_digits_hyphen_underscore")
    private String username;

    @ErrorCode(code = ApiErrorCode.INVALID_EMAIL)
    @NotNull(message = "email_required")
    @Size(min = Consts.EMAIL_MIN_LENGTH, max = Consts.EMAIL_MAX_LENGTH)
    @Email(message = "email_not_correct")
    private String email;

    @ErrorCode(code = ApiErrorCode.INVALID_PASSWORD)
    @Size(min = Consts.PASSWORD_MIN_SYMBOLS, max = Consts.PASSWORD_MAX_SYMBOLS, message = "password_length_8_256")
    @Pattern(regexp = "[0-9a-zA-Z_]*", message = "password_allow_latinchar_digits_underscore")
    private String password;


    @ErrorCode(code = ApiErrorCode.INVALID_USER_FIRST_NAME)
    @Size(min = Consts.FIRST_NAME_MIN_SYMBOLS, max = Consts.FIRST_NAME_MAX_SYMBOLS, message = "first_name_length_0_100")
    @Pattern(regexp = "[a-zA-Zа-яА-Я- ]*", message = "first_name_only_latin_cyrillic_character_digits_underscore_hyphen _allowed")
    private String firstName;

    @ErrorCode(code = ApiErrorCode.INVALID_USER_LAST_NAME)
    @Size(min = Consts.LAST_NAME_MIN_SYMBOLS, max = Consts.LAST_NAME_MAX_SYMBOLS, message = "last_name_length_0_100")
    @Pattern(regexp = "[a-zA-Zа-яА-Я- ]*", message = "last_name_only_latin_cyrillic_character_digits_underscore_hyphen _allowed")
    private String lastName;

    @ErrorCode(code = ApiErrorCode.INVALID_USER_DESCIPTION)
    @Size(min = Consts.DESCRIPTION_MIN_SYMBOLS, max = Consts.DESCRIPTION_MAX_SYMBOLS, message = "description_lower_1000")
    private String description;

    @ErrorCode(code = ApiErrorCode.INVALID_USER_BIRTH_DATE)
    @JsonDeserialize(using = ISOLocalDateDeserializer.class)
    //@Past
    private LocalDate birthDate;

    @ErrorCode(code = ApiErrorCode.INVALID_PHONE)
    @Size(min = Consts.PHONE_MIN_LENGTH, max = Consts.PHONE_MAX_LENGTH, message = "phone_length_1_15")
    @Pattern(regexp = "[0-9]*", message = "phone_allow_digits")
    private String phone;
}
