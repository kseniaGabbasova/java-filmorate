package com.example.filmorate.storage;

import com.example.filmorate.exception.NotFoundException;
import com.example.filmorate.model.Genre;

import java.util.List;

public interface GenreStorage {
    List<Genre> getAll();

    Genre getById(int id) throws NotFoundException;

    List<Genre> getByFilmId(int filmId) throws NotFoundException;

    void addAllToFilmId(int filmId, List<Genre> genres);

    void deleteAllByFilmId(int filmId);
}
