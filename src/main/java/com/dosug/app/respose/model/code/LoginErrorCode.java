package com.dosug.app.respose.model.code;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by radmir on 26.03.17.
 */
public enum LoginErrorCode implements ErrorCode {

    WRONG_LOGIN_AND_PASSWORD(1),
    INVALID_USERNAME(2),
    INVALID_PASSWORD(3);

    private int errorCode;

    LoginErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @JsonValue
    public int getErrorCode() {
        return errorCode;
    }
}
