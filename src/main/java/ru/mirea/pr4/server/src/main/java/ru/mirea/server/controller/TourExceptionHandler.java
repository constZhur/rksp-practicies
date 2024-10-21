package ru.mirea.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.mirea.server.exception.TourInvalidDataException;
import ru.mirea.server.exception.TourNotFoundException;

@ControllerAdvice
public class TourExceptionHandler {

    @ExceptionHandler(TourNotFoundException.class)
    public ResponseEntity<String> handleTourNotFoundException(TourNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TourInvalidDataException.class)
    public ResponseEntity<String> handleTourInvalidDataException(TourInvalidDataException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return new ResponseEntity<>("An unexpected error occurred: "
                + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
