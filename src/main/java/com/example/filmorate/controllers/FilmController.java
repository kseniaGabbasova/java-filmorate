package com.example.filmorate.controllers;

import com.example.filmorate.model.Film;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@Slf4j
@RestController
public class FilmController {
    private static final LocalDate BEGINNING = LocalDate.of(1895, 12, 28);
    private HashMap<Integer, Film> films = new HashMap<>();

    @PostMapping("/films")
    protected Film create(@Validated @RequestBody Film film) {
        if (save(film)) {
            return film;
        } else {
            throw new ValidationException();
        }
    }

    @PutMapping("/films")
    private Film update(@Validated @RequestBody Film film) {
        if (films.containsKey(film.getId()) && validate(film)) {
            films.replace(film.getId(), film);
            log.info("Фильм обновлен");
            return film;
        } else {
            log.warn("Фильма с айди не существует/не прошел валидацию");
            throw new ValidationException();
        }
    }

    @GetMapping("/films")
    private Collection<Film> getFilms() {
        return films.values();
    }

    private boolean validate(Film film) {
        if (film.getReleaseDate().isAfter(BEGINNING)) {
            return true;
        } else {
            log.info("Фильм не прошел валидацию");
            return false;
        }
    }

    private boolean save(Film film) {
        if (validate(film)) {
            int max = 0;
            if (films.isEmpty()) {
                film.setId(1);
            }
            for (int i : films.keySet()) {
                if (i > max) {
                    max = i;
                }
            }
            film.setId(max + 1);
            films.put(max + 1, film);
            log.info("Фильм добавлен");
            return true;
        } else {
            throw new ValidationException();
        }
    }
}
