package com.techtitans.infratrack.platform.shared.interfaces.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Traductor de exceptions hacia respuestas HTTP (ProblemDetail vía ErrorResponse).
 * Este handler define el borde entre el dominio y el cliente HTTP.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handleException(MethodArgumentNotValidException exception, Locale locale) {
        String prefix = messageSource.getMessage("errors.found", null, locale);
        String fields = exception.getFieldErrors().stream()
                .map(fieldError -> messageSource.getMessage(fieldError, locale))
                .collect(Collectors.joining(", "));
        log.warn("Validation failed: {}", fields);
        return ErrorResponse.create(
                exception,
                HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()),
                prefix + " " + fields
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handleException(IllegalArgumentException exception, Locale locale) {
        String messageKey = exception.getMessage() != null ? exception.getMessage() : "errors.found";
        String message = Objects.requireNonNullElse(messageSource.getMessage(messageKey, null, messageKey, locale), messageKey);
        log.warn("Illegal argument: {}", message);
        return ErrorResponse.create(exception, HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()), message);
    }
}

