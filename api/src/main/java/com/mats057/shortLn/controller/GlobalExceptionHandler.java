package com.mats057.shortLn.controller;

import com.mats057.shortLn.infrastructure.exceptions.InvalidUrlException;
import com.mats057.shortLn.infrastructure.exceptions.URLNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidUrlException.class)
    public ResponseEntity<Map<String, String>> handleInvalidUrl(InvalidUrlException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(URLNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(URLNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> error = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            sb.append(fieldError.getDefaultMessage()).append("; ");
        });
        String errorMsg = sb.length() > 0 ? sb.toString().trim() : "Invalid input";
        error.put("error", errorMsg);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}
