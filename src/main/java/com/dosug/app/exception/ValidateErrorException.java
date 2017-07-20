package com.dosug.app.exception;

import com.dosug.app.response.model.ApiError;
import lombok.Data;

import java.util.List;

@Data
public class ValidateErrorException extends Exception {

    List<ApiError> errors;

    public ValidateErrorException(List<ApiError> validateErrors) {

        errors = validateErrors;
    }
}
