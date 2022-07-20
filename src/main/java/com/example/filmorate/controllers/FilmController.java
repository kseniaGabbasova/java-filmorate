package com.example.filmorate.controllers;

import com.example.filmorate.exception.FilmNotFoundException;
import com.example.filmorate.exception.ValidationException;
import com.example.filmorate.model.Film;
import com.example.filmorate.service.FilmService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping("/films")
    protected Film create(@Validated @RequestBody Film film) throws ValidationException {
        return filmService.create(film);

    }

    @PutMapping("/films")
    private Film update(@Validated @RequestBody Film film) throws FilmNotFoundException {
        return filmService.update(film);
    }

    @GetMapping("/films")
    private Collection<Film> getFilms() {
        return filmService.getFilms();
    }

    @PutMapping("/films/{id}/like/{userId}")
    private void putLike(@PathVariable int id, @PathVariable int userId) throws FilmNotFoundException {
        filmService.putLike(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    private void deleteLike(@PathVariable int id, @PathVariable int userId) throws FilmNotFoundException {
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/films/popular")
    private List<Film> getByPopularity(@RequestParam(required = false, defaultValue = "10") Integer count) {
        return filmService.getByPopularity(count);
    }

    @GetMapping("/films/{id}")
    private Film getFilmById(@PathVariable int id) throws FilmNotFoundException {
        return filmService.getFilmById(id);
    }
}
