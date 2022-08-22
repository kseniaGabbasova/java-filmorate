package com.example.filmorate.service;

import com.example.filmorate.exception.NotFoundException;
import com.example.filmorate.model.Genre;
import com.example.filmorate.storage.GenreStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreStorage genreStorage;

    public List<Genre> getAll() {
        return genreStorage.getAll();
    }

    public Genre getById(int id) throws NotFoundException {
        return genreStorage.getById(id);
    }

    public List<Genre> getByFilmId(int filmId) throws NotFoundException {
        return genreStorage.getByFilmId(filmId);
    }

    public void updateForFilm(int filmId, List<Genre> genres) {
        genreStorage.deleteAllByFilmId(filmId);
        genreStorage.addAllToFilmId(filmId, genres);
    }
}