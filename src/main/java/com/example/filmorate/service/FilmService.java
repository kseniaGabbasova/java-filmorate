package com.example.filmorate.service;

import com.example.filmorate.exception.FilmNotFoundException;
import com.example.filmorate.exception.UserNotFoundException;
import com.example.filmorate.exception.ValidationException;
import com.example.filmorate.model.Film;
import com.example.filmorate.storage.FilmStorage;
import com.example.filmorate.storage.UserStorage;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
public class FilmService {
    private static final LocalDate BEGINNING = LocalDate.of(1895, 12, 28);
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final GenreService genreService;

    public FilmService(FilmStorage filmStorage, UserStorage userStorage, GenreService genreService) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.genreService = genreService;
    }

    public Film create(Film film) throws ValidationException {
        if (validate(film)) {
            filmStorage.create(film);
            if (film.getGenres() != null) {
                genreService.updateForFilm(film.getId(), film.getGenres());
            }
            return film;
        } else {
            throw new ValidationException();
        }
    }

    public Film update(Film film) throws FilmNotFoundException {
        if (validate(film) && filmStorage.getFilmById(film.getId()) != null) {
            if (film.getGenres() != null) {
                genreService.updateForFilm(film.getId(), film.getGenres());
            }
            return filmStorage.update(film);
        } else {
            throw new FilmNotFoundException();
        }
    }

    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film getFilmById(int id) throws FilmNotFoundException {
        if (filmStorage.getFilmById(id) != null) {
            return filmStorage.getFilmById(id);
        } else {
            throw new FilmNotFoundException();
        }
    }

    public void deleteLike(int id, int userId) throws FilmNotFoundException, UserNotFoundException {
        Film film = filmStorage.getFilmById(id);
        if (film != null && userStorage.getUserById(userId) != null) {
            filmStorage.deleteLike(film, userId);
        } else {
            throw new FilmNotFoundException();
        }
    }

    public void putLike(int id, int userId) throws FilmNotFoundException, UserNotFoundException {
        if (filmStorage.getFilmById(id) != null && userStorage.getUserById(userId) != null) {
            filmStorage.putLike(filmStorage.getFilmById(id), userId);
        } else {
            throw new FilmNotFoundException();
        }
    }

    public List<Film> getByPopularity(int count) {
        return filmStorage.getByPopularity(count);
    }

    private boolean validate(Film film) {
        return film.getReleaseDate().isAfter(BEGINNING);
    }
}
