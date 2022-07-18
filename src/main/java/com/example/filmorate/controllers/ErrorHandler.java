package com.example.filmorate.controllers;

import com.example.filmorate.controllers.exception.FilmNotFoundException;
import com.example.filmorate.controllers.exception.UserNotFoundException;
import com.example.filmorate.controllers.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice(assignableTypes = {FilmController.class, UserController.class})
public class ErrorHandler {

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(final ValidationException e) {
        return Map.of("Параметр не соответствует требованиям", "Введите подходящий под условия параметр");
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNullpointerException(final UserNotFoundException e) {
        return Map.of("Пользователь не найден", "Введите существующий айди");
    }

    @ExceptionHandler(FilmNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNullpointerException(final FilmNotFoundException e) {
        return Map.of("Фильм не найден", "Введите существующий айди");
    }
}
