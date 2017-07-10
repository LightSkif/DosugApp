package com.dosug.app.form;

import com.dosug.app.response.model.ApiErrorCode;
import com.dosug.app.utils.Consts;
import com.dosug.app.utils.ISOLocalDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class UpdateUserForm {

    @ErrorCode(code = ApiErrorCode.INVALID_USER_ID)
    @Min(value = Consts.MIN_ID, message = "id_lower_1")
    @NotNull(message = "user_id_required")
    private long userId;

    @ErrorCode(code = ApiErrorCode.INVALID_USER_FIRST_NAME)
    @Size(min = Consts.FIRST_NAME_MIN_SYMBOLS, max = Consts.FIRST_NAME_MAX_SYMBOLS, message = "first_name_length_0_100")
    @Pattern(regexp = "[a-zA-Zа-яА-Я- ]*", message = "first_name_only_latin_cyrillic_character_digits_underscore_hyphen _allowed")
    private String firstName;

    @ErrorCode(code = ApiErrorCode.INVALID_USER_LAST_NAME)
    @Size(min = Consts.LAST_NAME_MIN_SYMBOLS, max = Consts.LAST_NAME_MAX_SYMBOLS, message = "last_name_length_0_100")
    @Pattern(regexp = "[a-zA-Zа-яА-Я- ]*", message = "last_name_only_latin_cyrillic_character_digits_underscore_hyphen _allowed")
    private String lastName;

    @ErrorCode(code = ApiErrorCode.INVALID_USER_DESCIPTION)
    @NotNull(message = "description_field_required")
    @Size(min = Consts.DESCRIPTION_MIN_SYMBOLS, max = Consts.DESCRIPTION_MAX_SYMBOLS, message = "description_lower_1000")
    private String description;

    @JsonDeserialize(using = ISOLocalDateDeserializer.class)
    private LocalDate birthDate;
}
