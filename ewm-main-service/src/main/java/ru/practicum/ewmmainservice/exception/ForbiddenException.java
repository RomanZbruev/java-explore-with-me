package ru.practicum.ewmmainservice.exception;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String message) {
        super(message);
    }

    public String getMessage() {
        return super.getMessage();
    }
}
