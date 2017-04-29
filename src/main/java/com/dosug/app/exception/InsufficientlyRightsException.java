package com.dosug.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//  HTTP 403
@ResponseStatus(HttpStatus.FORBIDDEN)
public class InsufficientlyRightsException extends RuntimeException {
}
