package com.commerceplatform.api.orders.exceptions.handlers;

import com.commerceplatform.api.orders.exceptions.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ResourceHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> notFoundException(NotFoundException n) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponseDto
                .builder()
                .message(n.getMessage())
                .httpStatus(HttpStatus.NOT_FOUND)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDto> badRequestException(BadRequestException b) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponseDto
                .builder()
                .message(b.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build()
        );
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> methodArgumentNotValidException(MethodArgumentNotValidException m) {
        Map<String, String> messages = new HashMap<>();
        m.getBindingResult().getAllErrors().forEach(err -> {
            String field = ((FieldError) err).getField();
            String message = err.getDefaultMessage();
            messages.put(field, message);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponseDto
                .builder()
                .message(Arrays.toString(messages.entrySet().toArray()))
                .httpStatus(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDto> dataIntegrityViolationException(DataIntegrityViolationException d) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponseDto
                .builder()
                .message(d.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build()
        );
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponseDto> unauthorizedException(UnauthorizedException d) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponseDto
                .builder()
                .message(d.getMessage())
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .build()
        );
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponseDto> validationException(ValidationException d){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponseDto
            .builder()
            .message("Validation errors")
            .httpStatus(HttpStatus.BAD_REQUEST)
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .errors(d.getErrors())
            .build()
        );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponseDto> httpRequestMethodNotSupported(HttpRequestMethodNotSupportedException h) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(ErrorResponseDto
            .builder()
            .message(h.getMessage())
            .httpStatus(HttpStatus.METHOD_NOT_ALLOWED)
            .statusCode(HttpStatus.METHOD_NOT_ALLOWED.value())
            .build());
    }

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<ErrorResponseDto> restClientException(RestClientException r) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(ErrorResponseDto
            .builder()
            .message(r.getMessage())
            .httpStatus(HttpStatus.BAD_GATEWAY)
            .statusCode(HttpStatus.BAD_GATEWAY.value())
            .build());
    }

}