package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SocialMediaAPIException extends ResponseStatusException {
    public SocialMediaAPIException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public SocialMediaAPIException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }
}
