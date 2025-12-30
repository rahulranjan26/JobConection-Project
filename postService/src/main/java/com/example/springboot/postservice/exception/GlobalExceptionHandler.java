package com.example.springboot.postservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiError apiError = new ApiError(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException ex) {
        ApiError apiError = new ApiError(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {

        ApiError apiError = new ApiError(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
