package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.exception.SocialMediaAPIException;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(SocialMediaAPIException.class)
    public ResponseEntity<String> handleSocialMediaAPIException(SocialMediaAPIException ex) {
        return new ResponseEntity<>(ex.getReason(), ex.getStatus());
    }
}
