package com.infotecs.servicestorage.exceptions;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler(NoDataException.class)
    public ResponseEntity<String> NoDataExceptionHandler(NoDataException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<Violation>> MethodArgumentNotValidExceptionHandler(
            MethodArgumentNotValidException e
    ) {
        final List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation("Validation exception", error.getField(),
                        error.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ResponseEntity<List<Violation>>(violations, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Violation> HttpMessageNotReadableExceptionHandler(
            HttpMessageNotReadableException e
    ) {
        JsonMappingException jme = (JsonMappingException) e.getCause();
        String field = jme.getPath().get(0).getFieldName();
        return new ResponseEntity<Violation>(new Violation(
                "JSON parse exception", field, e.getMessage()
        ), HttpStatus.BAD_GATEWAY);
    }
}
