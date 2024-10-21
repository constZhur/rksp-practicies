package ru.mirea.server.exception;

public class TourInvalidDataException extends RuntimeException {
    public TourInvalidDataException(String message) {
        super(message);
    }
}
