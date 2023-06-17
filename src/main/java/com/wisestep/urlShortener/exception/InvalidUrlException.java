package com.wisestep.urlShortener.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidUrlException extends RuntimeException {
    public InvalidUrlException() {
    }

    public InvalidUrlException(String message) {
        super(message);
    }

}
