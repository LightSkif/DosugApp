package com.dosug.app.respose.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * all error codes for rest api
 */
public enum ApiErrorCode {

    UNKNOWN_SERVICE_PROBLEM(0),
    WRONG_LOGIN_AND_PASSWORD(1),
    INVALID_USERNAME(2),
    INVALID_PASSWORD(3),
    INVALID_EMAIL(4),
    INVALID_PASSWORD_RETRY(5),

    USERNAME_ALREADY_USE(6),
    EMAIL_ALREADY_USE(7);


    private int errorCode;

    ApiErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @JsonValue
    public int getErrorCode() {
        return errorCode;
    }
}
