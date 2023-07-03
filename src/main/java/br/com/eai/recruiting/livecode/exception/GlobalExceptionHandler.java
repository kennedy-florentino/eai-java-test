package br.com.eai.recruiting.livecode.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidVersionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleInvalidVersionException(InvalidVersionException ex, WebRequest req) {
        return ErrorMessage.builder()
                           .status(HttpStatus.BAD_REQUEST.value())
                           .message(ex.getMessage())
                           .description(req.getDescription(false))
                           .timestamp(Instant.now())
                           .build();
    }

    @ExceptionHandler(DuplicatedAddressException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleDuplicatedAddressException(DuplicatedAddressException ex, WebRequest req) {
        return ErrorMessage.builder()
                           .status(HttpStatus.BAD_REQUEST.value())
                           .message(ex.getMessage())
                           .description(req.getDescription(false))
                           .timestamp(Instant.now())
                           .build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleConstraintViolationException(ConstraintViolationException ex, WebRequest req) {
        return ErrorMessage.builder()
                           .status(HttpStatus.BAD_REQUEST.value())
                           .message(ex.getMessage())
                           .description(req.getDescription(false))
                           .timestamp(Instant.now())
                           .build();
    }
}
