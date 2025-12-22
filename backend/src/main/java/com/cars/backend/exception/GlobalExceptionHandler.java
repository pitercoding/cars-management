package com.cars.backend.exception;

import com.cars.backend.dto.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for the backend application.
 * Provides consistent error responses for various types of exceptions.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // =============================
    // 1. Bean Validation (@Valid)
    // =============================
    // Handles exceptions thrown when request body validation fails
    // Returns 400 Bad Request with the first validation error message
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Invalid request!");

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                message
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // =============================
    // 2. Illegal Argument Exception
    // =============================
    // Handles IllegalArgumentException typically thrown manually in the code
    // Returns 400 Bad Request with the exception message
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // =============================
    // 3. Entity Not Found (404)
    // =============================
    // Handles cases when requested entity does not exist in the database
    // Returns 404 Not Found with a descriptive message
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // =============================
    // 4. Bad Credentials (401)
    // =============================
    // Handles authentication failures (invalid username/password)
    // Returns 401 Unauthorized with a fixed error message
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                "Invalid username or password!"
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    // =============================
    // 5. Car Deletion Exception (400)
    // =============================
    // Custom exception for preventing deletion of cars with related data
    // Returns 400 Bad Request with details about related entities
    @ExceptionHandler(CarDeletionException.class)
    public ResponseEntity<String> handleCarDeletionException(CarDeletionException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("This car cannot be deleted because it has related data: " + ex.getDetails());
    }

    // =============================
    // 6. Fallback / Generic Exception (500)
    // =============================
    // Handles all other exceptions not specifically caught above
    // Returns 500 Internal Server Error with a generic message
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Unexpected server error!"
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}