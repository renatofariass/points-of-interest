package com.interest.points.exceptions.handler;

import com.interest.points.exceptions.BadRequestException;
import com.interest.points.exceptions.InvalidJwtAuthenticationException;
import com.interest.points.exceptions.ResourceNotFoundException;
import com.interest.points.exceptions.UnauthorizedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RestController
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleNotFoundExceptions(
            Exception ex, WebRequest request) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<ExceptionResponse> handleBadRequestExceptions(
            Exception ex, WebRequest request) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public final ResponseEntity<ExceptionResponse> handleUnauthorizedExceptions(
            Exception ex, WebRequest request) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionResponse);
    }

    @ExceptionHandler(InvalidJwtAuthenticationException.class)
    public final ResponseEntity<ExceptionResponse> handleInvalidJwtAuthenticationExceptions(
            Exception ex, WebRequest request) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exceptionResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ExceptionResponse exceptionResponse = new ExceptionResponseWithFieldErrors(
                new Date(),
                "Null or empty fields",
                request.getDescription(false),
                errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }
}
