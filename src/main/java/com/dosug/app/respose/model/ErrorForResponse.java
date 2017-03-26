package com.dosug.app.respose.model;

import com.dosug.app.respose.model.code.ErrorCode;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by radmir on 26.03.17.
 */

public class ErrorForResponse {
    @JsonProperty
    private ErrorCode errorCode;

    @JsonProperty
    private String message;

    public ErrorForResponse(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
