package com.example.filmorate.controllers;

import com.example.filmorate.model.Film;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilmControllerTest {
    private FilmController controller;
    private Film film;

    @BeforeEach
    void init() {
        controller = new FilmController();
        film = new Film(null, "film", "description",
                LocalDate.of(1800, 12, 1), 25);
    }

    @Test
    void releaseDateValidationTest() {
        assertThrows(ValidationException.class, () -> controller.create(film));
        film.setReleaseDate(LocalDate.now());
        assertEquals(film, controller.create(film));
    }
}