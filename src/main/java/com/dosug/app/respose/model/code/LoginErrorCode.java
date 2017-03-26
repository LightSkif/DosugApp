package com.dosug.app.respose.model.code;

import com.dosug.app.respose.model.code.ErrorCode;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by radmir on 26.03.17.
 */
public enum LoginErrorCode implements ErrorCode {

    WRONG_LOGIN_AND_PASSWORD(1),
    INVALID_USERNAME(2),
    INVALID_PASSWORD(3);

    private int numVal;

    LoginErrorCode(int numVal) {
        this.numVal = numVal;
    }

    @JsonValue
    public int getNumVal() {
        return numVal;
    }
}
