package com.example.filmorate.service;

import com.example.filmorate.model.Film;
import com.example.filmorate.storage.FilmStorage;
import com.example.filmorate.storage.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
public class FilmService {
    private static final LocalDate BEGINNING = LocalDate.of(1895, 12, 28);
    @Autowired
    FilmStorage filmStorage;
    @Autowired
    UserStorage userStorage;

    public Film create(Film film) {
        if (validate(film)) {
            return filmStorage.create(film);
        } else {
            return null;
        }
    }

    public Film update(Film film) {
        if (validate(film) && filmStorage.getFilmById(film.getId()) != null) {
            return filmStorage.update(film);
        } else {
            return null;
        }
    }

    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film getFilmById(int id) {
        return filmStorage.getFilmById(id);
    }

    public boolean deleteLike(int id, int userId) {
        Film film = filmStorage.getFilmById(id);
        if (film != null && userStorage.getUserById(userId) != null) {
            filmStorage.deleteLike(film, userId);
            return true;
        } else {
            return false;
        }
    }

    public boolean putLike(int id, int userId) {
        Film film = filmStorage.getFilmById(id);
        if (film != null && userStorage.getUserById(userId) != null) {
            filmStorage.putLike(film, userId);
            return true;
        } else {
            return false;
        }
    }

    public List<Film> getByPopularity(int count) {
        return filmStorage.getByPopularity(count);
    }

    private boolean validate(Film film) {
        return film.getReleaseDate().isAfter(BEGINNING);
    }
}
