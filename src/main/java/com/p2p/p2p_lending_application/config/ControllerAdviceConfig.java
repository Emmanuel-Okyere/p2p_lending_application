package com.p2p.p2p_lending_application.config;

import com.p2p.p2p_lending_application.authentication.exception.TokenRefreshException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

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

    @ExceptionHandler(TokenRefreshException.class)
    public ResponseEntity<Map<String, ?>> handleRefreshTokenException(TokenRefreshException tokenRefreshException, WebRequest webRequest){
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(Map
                        .of("error",tokenRefreshException
                                .getMessage()));
    }
}
