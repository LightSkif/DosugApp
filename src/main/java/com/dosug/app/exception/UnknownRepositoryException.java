package com.dosug.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//  HTTP 500
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UnknownRepositoryException extends RuntimeException {
}
