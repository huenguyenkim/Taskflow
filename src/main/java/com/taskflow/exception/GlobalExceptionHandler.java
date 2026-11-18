
package com.taskflow.exception;

import jakarta.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 1. Resource not found: HTTP 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>>
    handleResourceNotFoundException(ResourceNotFoundException ex) {

        Map<String, Object> response = createResponse(
                HttpStatus.NOT_FOUND,
                "Not Found",
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    // 2. Bad request: HTTP 400
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>>
    handleBadRequestException(BadRequestException ex) {

        Map<String, Object> response = createResponse(
                HttpStatus.BAD_REQUEST,
                "Bad Request",
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // 3. DTO validation: HTTP 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>>
    handleValidationException(MethodArgumentNotValidException ex) {

        Map<String, String> fieldErrors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {

                    String fieldName = error.getField();

                    String errorMessage = error.getDefaultMessage();

                    fieldErrors.put(fieldName, errorMessage);
                });

        Map<String, Object> response = createResponse(
                HttpStatus.BAD_REQUEST,
                "Validation Failed",
                "Please check your input data"
        );

        response.put("errors", fieldErrors);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // 4. Constraint violation: HTTP 400
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>>
    handleConstraintViolationException(
            ConstraintViolationException ex) {

        Map<String, Object> response = createResponse(
                HttpStatus.BAD_REQUEST,
                "Constraint Violation",
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // 5. Unsupported HTTP method: HTTP 405
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, Object>>
    handleMethodNotSupported(
            HttpRequestMethodNotSupportedException ex) {

        Map<String, Object> response = createResponse(
                HttpStatus.METHOD_NOT_ALLOWED,
                "Method Not Allowed",
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(response);
    }

    // 6. Unexpected errors: HTTP 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>>
    handleException(Exception ex) {

        logger.error("Unexpected server error", ex);

        Map<String, Object> response = createResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                "An unexpected error occurred"
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    // 7. Common response builder
    private Map<String, Object> createResponse(
            HttpStatus status,
            String error,
            String message) {

        Map<String, Object> response = new HashMap<>();

        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", error);
        response.put("message", message);

        return response;
    }
}
