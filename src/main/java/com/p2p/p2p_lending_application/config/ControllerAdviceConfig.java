package com.p2p.p2p_lending_application.config;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.p2p.p2p_lending_application.dto.responseDTO.ErrorResponseDTO;
import com.p2p.p2p_lending_application.exceptions.BadRequest400Exception;
import com.p2p.p2p_lending_application.exceptions.Duplicate409Exception;
import com.p2p.p2p_lending_application.exceptions.NotFound404Exception;
import com.p2p.p2p_lending_application.exceptions.TokenRefreshException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
    public ResponseEntity<Map<String, ?>> handleEntityValidationExceptions(MethodArgumentNotValidException methodArgumentNotValidException) {

        Map<String, String> body = methodArgumentNotValidException
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        Map<String, ?> errors = Map.
                of("errors", body, "status", "failure", "message", "Creation Failed");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
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
    @ExceptionHandler(NotFound404Exception.class)
    public ResponseEntity<ErrorResponseDTO> handle404NotFoundException(NotFound404Exception notFound404Exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(notFound404Exception.getMessage(), "failure"));
    }

    @ExceptionHandler(BadRequest400Exception.class)
    public ResponseEntity<ErrorResponseDTO> handle400BadRequestException(BadRequest400Exception badRequest400Exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO(badRequest400Exception.getMessage(), "failure"));
    }
    @ExceptionHandler(Duplicate409Exception.class)
    public ResponseEntity<ErrorResponseDTO> handle409NotFoundException(Duplicate409Exception duplicate409Exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDTO(duplicate409Exception.getMessage(), "failure"));
    }

    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<Map<String, String>> handleJSONParseError(HttpMessageNotReadableException httpMessageNotReadableException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("status", "failure", "message", "could not parse state input"));
    }

    @ExceptionHandler(JWTDecodeException.class)
    public ResponseEntity<Map<String, String>> handleJwtDecodeException(JWTDecodeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("status", "failure", "message", e.getMessage()));
    }
}
