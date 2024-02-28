package com.luissoy.spaceshipmongo.controller;

import com.luissoy.spaceshipmongo.exception.DataAutoIdException;
import com.luissoy.spaceshipmongo.exception.DataNotFoundException;
import com.luissoy.spaceshipmongo.response.StandardErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<?> handleDataNotFoundException(DataNotFoundException e) {
        String message = e.getMessage() == null ? "Data not found" : e.getMessage();
        return ResponseEntity.
                status(HttpStatus.NOT_FOUND).
                body(new StandardErrorResponse(message, e.getClass().getName()));
    }

    @ExceptionHandler(DataAutoIdException.class)
    public ResponseEntity<?> handleDataAutoIdException(DataAutoIdException e) {
        String message = e.getMessage() == null ? "Auto Database Id Exception" : e.getMessage();
        return ResponseEntity.
                status(HttpStatus.INTERNAL_SERVER_ERROR).
                body(new StandardErrorResponse(message, e.getClass().getName()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        String message = e.getMessage() == null ? "General exception" : e.getMessage();
        return ResponseEntity.
                status(HttpStatus.INTERNAL_SERVER_ERROR).
                body(new StandardErrorResponse(message, e.getClass().getName()));
    }

}
