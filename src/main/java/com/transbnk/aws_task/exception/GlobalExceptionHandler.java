package com.transbnk.aws_task.exception;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = IOException.class)
    public ResponseEntity<?> handleIOException(IOException exception) {
        return ResponseEntity.badRequest().body("IOException: " + exception.getMessage());
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<?> handleNullException(NullPointerException exception) {
        return ResponseEntity.badRequest().body("NullPointerException: " + exception.getMessage());
    }

}
