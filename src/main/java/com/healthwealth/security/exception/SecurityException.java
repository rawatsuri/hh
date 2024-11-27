package com.healthwealth.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class SecurityException extends RuntimeException {
    public SecurityException(String message) {
        super(message);
    }
} 