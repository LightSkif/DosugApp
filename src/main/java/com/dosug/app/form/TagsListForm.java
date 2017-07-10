package com.dosug.app.form;

import com.dosug.app.response.model.ApiErrorCode;
import com.dosug.app.utils.Consts;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Created by radmir on 29.05.17.
 */
@Data
public class TagsListForm {

    @ErrorCode(code = ApiErrorCode.INVALID_EVENT_TAGS)
    @NotNull(message = "tags_required")
    @Size(min = Consts.TAG_MIN_AMOUNT, max = Consts.TAG_MAX_AMOUNT, message = "tags_length_1_10")
    private List<String> tags;


    @ErrorCode(code = ApiErrorCode.INVALID_EVENT_TAG)
    @AssertTrue(message = "wrong_tag")
    public boolean isRightSizeTag() {
        if (tags != null) {
            Pattern regexPattern = Pattern.compile("[a-zA-Zа-яА-Я0-9-_]*");
            Optional<String> tagMistake = tags.stream()
                    .filter(s -> ((s.length() > Consts.TAG_MAX_SYMBOLS) ||
                            (s.length() < Consts.TAG_MIN_SYMBOLS) || !regexPattern.matcher(s).matches()))
                    .findFirst();

            // В случае если ни одной ошибки не найдено, проверка завершена успешно.
            return tagMistake.equals(Optional.empty());
        }
        return true;
    }
}
