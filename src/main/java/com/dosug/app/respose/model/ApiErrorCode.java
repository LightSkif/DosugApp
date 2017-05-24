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

    INVALID_EVENT_ID(8),
    INVALID_EVENT_NAME(9),
    INVALID_EVENT_CONTENT(10),
    INVALID_EVENT_DATE(11),
    INVALID_EVENT_TAGS(12),
    INVALID_EVENT_TAG(13),
    INVALID_PLACE_NAME(14),
    INVALID_LONGITUDE(15),
    INVALID_LATITUDE(16),
    INVALID_PERIOD(17),

    INVALID_USER_ID(18),
    INVALID_FIRSTNAME(19),
    INVALID_LASTNAME(20),
    INVALID_DESCRIPTION(21),
    INVALID_USER_TAGS(22),
    INVALID_USER_TAG(23),

    INVALID_NEW_PASSWORD_RETRY(24),
    INVALID_NEW_PASSWORD(25);



    private int errorCode;

    ApiErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @JsonValue
    public int getErrorCode() {
        return errorCode;
    }
}
