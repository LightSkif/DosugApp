package com.dosug.app.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//  HTTP 409
@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {
}
