package com.calderon.denv.pep.exception;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Hidden
public class GlobalExceptionHandler {
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
    ErrorDetails errorDetails =
        ErrorDetails.builder()
            .timestamp(LocalDateTime.now())
            .message(ex.getMessage())
            .details(request.getDescription(false))
            .build();
    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler({DataNotFoundException.class})
  public ResponseEntity<ErrorDetails> handleResourceNotFoundException(
      DataNotFoundException ex, WebRequest request) {
    ErrorDetails errorDetails =
        new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({BadCredentialsException.class})
  public ResponseEntity<ErrorDetails> handleBadCredentialsException(
      BadCredentialsException ex, WebRequest request) {
    ErrorDetails errorDetails =
        new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<InputDataError> handleValidationException(
      MethodArgumentNotValidException ex, WebRequest request) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getFieldErrors()
        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

    InputDataError inputDataError =
        InputDataError.builder()
            .timestamp(LocalDateTime.now())
            .message("Input data validation failed")
            .details(request.getDescription(false))
            .errors(errors)
            .build();

    return new ResponseEntity<>(inputDataError, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<InputDataError> handleConstraintViolation(ConstraintViolationException ex) {
    Map<String, String> errors =
        ex.getConstraintViolations().stream()
            .collect(
                Collectors.toMap(
                    v -> v.getPropertyPath().toString(),
                    ConstraintViolation::getMessage,
                    (msg1, msg2) -> msg1));

    InputDataError error =
        InputDataError.builder()
            .timestamp(LocalDateTime.now())
            .message("Validation failed")
            .details("Invalid input parameters")
            .errors(errors)
            .build();

    return ResponseEntity.badRequest().body(error);
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorDetails> handleBadRequestException(
      BadRequestException ex, WebRequest request) {
    ErrorDetails errorDetails =
        new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }
}
