package ru.yandex.practicum.filmorate.exceptions;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {
    public ValidationException(String s) {
        super(s);
    }
}
