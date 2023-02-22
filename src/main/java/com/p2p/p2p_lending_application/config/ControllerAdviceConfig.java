package com.p2p.p2p_lending_application.config;

import com.p2p.p2p_lending_application.authentication.exception.TokenRefreshException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@ControllerAdvice
public class ControllerAdviceConfig {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,?>> handleEntityValidationException(MethodArgumentNotValidException methodArgumentNotValidException){
        Map <String, String> body = methodArgumentNotValidException
                .getBindingResult()
                .getFieldErrors()
                .stream().collect(Collectors.toMap(FieldError::getField, FieldError::getField));
        Map<String, ?> errors = Map.
                of("errors", body);
        return ResponseEntity.status(HttpStatus.OK).body(errors);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleEntityOfEntityValidation(ConstraintViolationException constraintViolationException){
        Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
        List<?> news =  violations.stream().map(violation -> Objects.requireNonNull(StreamSupport.stream(
                                violation.getPropertyPath().spliterator(), false).
                        reduce((first, second) -> second).
                        orElse(null)).
                toString()).toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("fieldErrors",news));
    }
    @ExceptionHandler(TokenRefreshException.class)
    public ResponseEntity<Map<String, ?>> handleRefreshTokenException(TokenRefreshException tokenRefreshException, WebRequest webRequest){
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(Map
                        .of("error",tokenRefreshException
                                .getMessage()));
    }
}
