package com.example.filmorate.controllers;

import com.example.filmorate.controllers.exception.FilmNotFoundException;
import com.example.filmorate.controllers.exception.ValidationException;
import com.example.filmorate.model.Film;
import com.example.filmorate.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
public class FilmController {

    @Autowired
    FilmService filmService;

    @PostMapping("/films")
    protected Film create(@Validated @RequestBody Film film) {
        Film filmToReturn = filmService.create(film);
        if (filmToReturn != null) {
            return filmToReturn;
        } else {
            throw new ValidationException();
        }
    }

    @PutMapping("/films")
    private Film update(@Validated @RequestBody Film film) throws FilmNotFoundException {
        if (filmService.update(film) != null) {
            return filmService.update(film);
        } else {
            throw new FilmNotFoundException();
        }
    }

    @GetMapping("/films")
    private Collection<Film> getFilms() {
        return filmService.getFilms();
    }

    @PutMapping("/films/{id}/like/{userId}")
    private void putLike(@PathVariable int id, @PathVariable int userId) throws FilmNotFoundException {
        boolean result = filmService.putLike(id, userId);
        if (!result) {
            throw new FilmNotFoundException();
        }

    }

    @DeleteMapping("/films/{id}/like/{userId}")
    private void deleteLike(@PathVariable int id, @PathVariable int userId) throws FilmNotFoundException {
        boolean result = filmService.deleteLike(id, userId);
        if (!result) {
            throw new FilmNotFoundException();
        }
    }

    @GetMapping("/films/popular")
    private List<Film> getByPopularity(@RequestParam(required = false) Integer count) {
        if (count == null) {
            count = 10;
        }
        return filmService.getByPopularity(count);
    }

    @GetMapping("/films/{id}")
    private Film getFilmById(@PathVariable int id) throws FilmNotFoundException {
        if (filmService.getFilmById(id) != null) {
            return filmService.getFilmById(id);
        } else {
            throw new FilmNotFoundException();
        }
    }
}
