package com.dosug.app.form;

import com.dosug.app.respose.model.ApiErrorCode;
import com.dosug.app.utils.LocalDateTimeDeserializer;
import com.dosug.app.utils.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class UpdateUserForm {

    public static final int MIN_ID = 1;

    public static final int USERNAME_MIN_SYMBOLS = 1;

    public static final int USERNAME_MAX_SYMBOLS = 256;

    public static final int FIRSTNAME_MIN_SYMBOLS = 1;

    public static final int FIRSTNAME_MAX_SYMBOLS = 128;

    public static final int LASTNAME_MIN_SYMBOLS = 1;

    public static final int LASTNAME_MAX_SYMBOLS = 128;

    public static final int TAG_MIN_SYMBOLS = 1;

    public static final int TAG_MAX_SYMBOLS = 256;

    public static final int TAG_MIN_AMOUNT = 1;

    public static final int TAG_MAX_AMOUNT = 10;


    @ErrorCode(code = ApiErrorCode.INVALID_EVENT_ID)
    @Min(value = MIN_ID, message = "id is lower than {value}")
    @NotNull(message = "eventId field is required")
    private long eventId;

    @ErrorCode(code = ApiErrorCode.INVALID_USERNAME)
    @NotNull(message = "username field is required")
    @Size(min = USERNAME_MIN_SYMBOLS, max = USERNAME_MAX_SYMBOLS, message = "username length from 1 to 256")
    @Pattern(regexp = "[a-zA-Z0-9-_]*", message = "only latin character, digits, underline and hyphen allowed in username")
    private String username;

    @ErrorCode(code = ApiErrorCode.INVALID_FIRSTNAME)
    @Size(min = FIRSTNAME_MIN_SYMBOLS, max = FIRSTNAME_MAX_SYMBOLS, message = "firstName should be shorter than 100 characters")
    @Pattern(regexp = "[a-zA-Zа-яА-Я ]*", message = "only character and space allowed in firstName")
    private String firstName;

    @ErrorCode(code = ApiErrorCode.INVALID_LASTNAME)
    @Size(min = LASTNAME_MIN_SYMBOLS, max = LASTNAME_MAX_SYMBOLS, message = "firstName should be shorter than 100 characters")
    @Pattern(regexp = "[a-zA-Zа-яА-Я ]*", message = "only character and space allowed in firstName")
    private String lastName;

    private String avatar;

    @ErrorCode(code = ApiErrorCode.INVALID_DESCRIPTION)
    @NotNull(message = "description field is required")
    @Size(min = FIRSTNAME_MIN_SYMBOLS, max = FIRSTNAME_MAX_SYMBOLS, message = "firstName should be shorter than 100 characters")
    private String description;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDate birthDate;

    @ErrorCode(code = ApiErrorCode.INVALID_USER_TAGS)
    @NotNull(message = "tags is required")
    @Size(min = TAG_MIN_AMOUNT, max = TAG_MAX_AMOUNT, message = "from one to ten tag is required")
    private ArrayList<String> tags;

    @ErrorCode(code = ApiErrorCode.INVALID_USER_TAG)
    @AssertTrue(message = "wrong tag")
    public boolean isRightSizeTag() {

        if (tags != null) {
            java.util.regex.Pattern regexPattern = java.util.regex.Pattern.compile("[a-zA-Zа-яА-Я0-9-_]*");
            Optional<String> tagMistake = tags.stream()
                    .filter(s -> ((s.length() > TAG_MAX_SYMBOLS) ||
                            (s.length() < TAG_MIN_SYMBOLS) || !regexPattern.matcher(s).matches()))
                    .findFirst();

            // В случае если ни одной ошибки не найдено, проверка завершена успешно.
            return tagMistake.equals(Optional.empty());
        }

        return true;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
}
