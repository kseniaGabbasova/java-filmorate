package com.example.filmorate.storage;

import com.example.filmorate.model.Film;

import java.util.Collection;
import java.util.List;

public interface FilmStorage {
    Film create(Film film);

    Film update(Film film);

    Collection<Film> getFilms();

    Film getFilmById(int id);

    void deleteLike(Film film, int userId);

    void putLike(Film film, int userId);

    List<Film> getByPopularity(int count);
}
