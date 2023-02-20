package com.dmp.jobsapi;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(value = { ResponseStatusException.class })
    public ResponseEntity<ProblemDetail> handleRestException(ResponseStatusException ex) {
        ProblemDetail detail = ex.getBody();
        detail.setType(URI.create("/errors/" + detail.getTitle().toLowerCase().replace(" ", "-")));
        return ResponseEntity.of(detail).build();
    }

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public ResponseEntity<ProblemDetail> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        detail.setTitle("Invalid Value");;
        detail.setType(URI.create("/errors/validations"));
        return ResponseEntity.of(detail).build();
    }

    @ExceptionHandler(value = { NoHandlerFoundException.class })
    public ResponseEntity<ProblemDetail> handleRouteNotFoundException(NoHandlerFoundException ex, HttpServletRequest request) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
            HttpStatus.NOT_FOUND, 
            "Route " + request.getMethod() + " " +  request.getRequestURI() + " not found."
        );
        detail.setType(URI.create("/errors/" + detail.getTitle().toLowerCase().replace(" ", "-")));
        return ResponseEntity.of(detail).build();
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handleUnexpectedException(Exception ex) {
        ex.printStackTrace();
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong.");
        detail.setType(URI.create("/errors/" + detail.getTitle().toLowerCase().replace(" ", "-")));
        return ResponseEntity.of(detail).build();
    }
}