package com.dosug.app.response.model;

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
    INVALID_PHONE(19),
    INVALID_USER_DESCIPTION(20),
    INVALID_USER_BIRTH_DATE(21),
    INVALID_USER_LAST_NAME(22),
    INVALID_USER_FIRST_NAME(23),
    INVALID_USER_TAGS(24),
    INVALID_USER_TAG(25),

    INVALID_NEW_PASSWORD_RETRY(26),
    INVALID_NEW_PASSWORD(27),
    INVALID_TAG_ID(28),

    INVALID_IMAGE_SIZE(29),
    INVALID_IMAGE_EVENT_ID(30),
    INVALID_IMAGE_EVENT_CREATOR(31),
    INVALID_IMAGE_EXTENSION(32);


    private int errorCode;

    ApiErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @JsonValue
    public int getErrorCode() {
        return errorCode;
    }
}
