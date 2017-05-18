package com.dosug.app.respose.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by radmir on 26.03.17.
 */

public class ApiError {
    @JsonProperty
    private ApiErrorCode errorCode;

    @JsonProperty
    private String message;

    public ApiError(ApiErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public ApiErrorCode getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
