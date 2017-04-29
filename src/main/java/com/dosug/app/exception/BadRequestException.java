package com.dosug.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//  HTTP 400
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException {
}
