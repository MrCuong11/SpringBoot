package com.example.authdemo.exception;

import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
// nhiệm vụ của class này là xử lý các exception toàn cục
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleInvalidJson(HttpMessageNotReadableException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                // trả về body là một map với key là error và message
                .body(Map.of(
                        "error", "Invalid JSON format",
                        "message", ex.getMostSpecificCause().getMessage()
                ));
    }

}
