package com.dosug.app.form;

import com.dosug.app.response.model.ApiErrorCode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация которая ставится над полем формы
 * и в случае невалидного поля мы знаем
 * какой код ошибки для этого поля можно отправить клиенту
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ErrorCode {
    ApiErrorCode code();
}
