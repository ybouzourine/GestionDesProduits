package com.bouzourine.gestiondesproduits.exceptionhandlers;
import com.bouzourine.gestiondesproduits.exceptions.EntityAlreadyExistsException;
import com.bouzourine.gestiondesproduits.exceptions.EntityException;
import com.bouzourine.gestiondesproduits.exceptions.EntityInvalidException;
import com.bouzourine.gestiondesproduits.exceptions.EntityNotFoundException;
import com.bouzourine.gestiondesproduits.utils.ErrorDto;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class DefaultControllerExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class,
            InvalidDataAccessApiUsageException.class})
    public ResponseEntity<String> handleIllegalArgumentException(RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        final String message = "JSON_PARSE_ERROR";
        final HttpStatus alreadyExists = HttpStatus.BAD_REQUEST;
        final ErrorDto errorDto = ErrorDto.builder()
                .httpCode(alreadyExists.value())
                .message(message)
                .errors(Collections.singletonList(e.getMessage()))
                .build();
        return new  ResponseEntity<>(errorDto, alreadyExists);

    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getAllErrors().stream().map(objectError -> {
                    if (objectError instanceof FieldError error) {
                        return String.format("%s : %s", error.getField(), error.getDefaultMessage());
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .toList();
        final String message = "ETITY_%s_CONTAINS_INVALID_FIELDS";
        final String targetClasseName = e.getTarget().getClass().getSimpleName();
        final HttpStatus alreadyExists = HttpStatus.BAD_REQUEST;
        final ErrorDto errorDto = ErrorDto.builder()
                .httpCode(alreadyExists.value())
                .errors(errors)
                .message(String.format(message , targetClasseName))
                .build();
        return new  ResponseEntity<>(errorDto, alreadyExists);
    }

    @ExceptionHandler(EntityException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(EntityException e) {
        HttpStatus httpStatus;
        if (e instanceof EntityInvalidException) {
            httpStatus = HttpStatus.BAD_REQUEST;
        } else if (e instanceof EntityNotFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;
        } else if (e instanceof EntityAlreadyExistsException) {
            httpStatus = HttpStatus.CONFLICT;
        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        final ErrorDto errorDto = ErrorDto.builder()
                .httpCode(httpStatus.value())
                .errors(e.getErrors())
                .message(e.getMessage())
                .build();
        return new  ResponseEntity<>(errorDto, httpStatus);
    }
}