package com.dosug.app.form;

import com.dosug.app.respose.model.ApiErrorCode;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Created by radmir on 29.05.17.
 */
public class TagsListForm {

    private final static int TAG_MIN_AMOUNT = 0;

    private final static int TAG_MAX_AMOUNT = 250;

    private final static int TAG_MAX_SYMBOLS = 50;

    private final static int TAG_MIN_SYMBOLS = 1;

    @ErrorCode(code = ApiErrorCode.INVALID_EVENT_TAGS)
    @NotNull(message = "tags_required")
    @Size(min = TAG_MIN_AMOUNT, max = TAG_MAX_AMOUNT, message = "tags_length_1_10")
    private List<String> tags;


    @ErrorCode(code = ApiErrorCode.INVALID_EVENT_TAG)
    @AssertTrue(message = "wrong_tag")
    public boolean isRightSizeTag() {
        if (tags != null) {
            Pattern regexPattern = Pattern.compile("[a-zA-Zа-яА-Я0-9-_]*");
            Optional<String> tagMistake = tags.stream()
                    .filter(s -> ((s.length() > TAG_MAX_SYMBOLS) ||
                            (s.length() < TAG_MIN_SYMBOLS) || !regexPattern.matcher(s).matches()))
                    .findFirst();

            // В случае если ни одной ошибки не найдено, проверка завершена успешно.
            return tagMistake.equals(Optional.empty());
        }
        return true;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getTags() {
        return tags;
    }
}
