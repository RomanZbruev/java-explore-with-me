package ru.practicum.ewmmainservice.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandler {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(NotFoundException exception) {
        return ApiError.builder()
                .message(exception.getMessage())
                .reason("There are no object has requested.")
                .status("NOT_FOUND")
                .timestamp(LocalDateTime.now().format(FORMATTER))
                .build();
    }

    @ExceptionHandler({BadRequestException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequestException(BadRequestException badRequestException) {
        return ApiError.builder()
                .message(badRequestException.getMessage())
                .reason("Request contains wrong data.")
                .status("BAD_REQUEST")
                .timestamp(LocalDateTime.now().format(FORMATTER))
                .build();
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleForbiddenException(ForbiddenException exception) {
        return ApiError.builder()
                .message(exception.getMessage())
                .reason("There are no rights, access has forbidden.")
                .status("FORBIDDEN")
                .timestamp(LocalDateTime.now().format(FORMATTER))
                .build();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        return ApiError.builder()
                .message(Objects.requireNonNull(exception.getMessage()).split(";")[0])
                .reason("Request contains invalid values.")
                .status("CONFLICT")
                .timestamp(LocalDateTime.now().format(FORMATTER))
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return ApiError.builder()
                .errors(errors)
                .message("Validation has failed.")
                .reason("Request contains wrong data.")
                .status("BAD_REQUEST")
                .timestamp(LocalDateTime.now().format(FORMATTER))
                .build();
    }


}