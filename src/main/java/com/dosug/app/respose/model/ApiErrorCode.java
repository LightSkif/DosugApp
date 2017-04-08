package com.dosug.app.respose.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * all error codes for rest api
 */
public enum ApiErrorCode {

    WRONG_LOGIN_AND_PASSWORD(1),
    INVALID_USERNAME(2),
    INVALID_PASSWORD(3);

    private int errorCode;

    ApiErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @JsonValue
    public int getErrorCode() {
        return errorCode;
    }
}
