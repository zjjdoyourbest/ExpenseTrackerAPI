package org.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.DateTimeException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidFormat(HttpMessageNotReadableException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Incorrect request format or field type mismatch");
        body.put("details", ex.getLocalizedMessage());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<Map<String, Object>> handleDatePrase(DateTimeException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Date or time formatting error");
        body.put("details", ex.getLocalizedMessage());
        return ResponseEntity.badRequest().body(body);
    }


}
