package com.wisestep.urlShortener.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.GONE)
public class ExpiredLinkException extends RuntimeException {

    public ExpiredLinkException() {
    }

    public ExpiredLinkException(String message) {
        super(message);
    }

}
