package com.dosug.app.respose.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * all error codes for rest api
 * возможно далее предстоит разбить на ошибки валидации и
 * ошибки в логике(Например: USERNAME_ALREADY_USER)
 */
public enum ApiErrorCode {

    UNKNOWN_SERVICE_PROBLEM(0),
    WRONG_LOGIN_AND_PASSWORD(1),
    INVALID_USERNAME(2),
    INVALID_PASSWORD(3),
    INVALID_EMAIL(4),
    INVALID_PASSWORD_RETRY(5),

    USERNAME_ALREADY_USE(6),
    EMAIL_ALREADY_USE(7),

    INVALID_EVENT_NAME(8),
    INVALID_EVENT_CONTENT(9),
    INVALID_EVENT_DATE(10),
    INVALID_EVENT_TAGS(11),
    INVALID_EVENT_TAG(12),
    INVALID_ALTITUDE(13),
    INVALID_LATITUDE(14);

    private int errorCode;

    ApiErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @JsonValue
    public int getErrorCode() {
        return errorCode;
    }
}
