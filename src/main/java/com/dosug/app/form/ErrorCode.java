package com.dosug.app.form;

import com.dosug.app.respose.model.ApiErrorCode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация которая ставится над полем формы
 * и в случае невалидного поля мы знаем
 * какой код ошибки для этого поля можно отправить клиенту
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ErrorCode {
    ApiErrorCode code();
}
