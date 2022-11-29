package ru.practicum.ewmmainservice.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }

    public String getMessage() {
        return super.getMessage();
    }
}
