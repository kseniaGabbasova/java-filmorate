package com.example.filmorate;

import com.example.filmorate.exception.NotFoundException;
import com.example.filmorate.model.Film;
import com.example.filmorate.model.Genre;
import com.example.filmorate.model.Mpa;
import com.example.filmorate.storage.FilmDbStorage;
import com.example.filmorate.storage.GenreStorage;
import com.example.filmorate.storage.MpaStorage;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmTest {
    private final FilmDbStorage filmStorage;
    private final MpaStorage mpaStorage;
    private final GenreStorage genreStorage;

    @Test
    void ValidFilmTest() {
        Film testFilm = Film.builder()
                .name("name")
                .description("description")
                .duration(170)
                .releaseDate(LocalDate.ofEpochDay(1999 - 01 - 01))
                .mpa(Mpa.builder().id(1).build())
                .build();

        Integer filmId = filmStorage.create(testFilm).getId();

        Optional<Film> filmOptional = Optional.ofNullable(filmStorage.getFilmById(filmId));
        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("id", filmId)
                );
    }

    @Test
    void getByIdNotValidTest() {
        assertThrows(NotFoundException.class, () -> filmStorage.getFilmById(300));
    }

    @Test
    void ValidMpaTest() {
        Optional<Mpa> mpaOptional = Optional.ofNullable(mpaStorage.getById(1));
        assertThat(mpaOptional)
                .isPresent()
                .hasValueSatisfying(mpa ->
                        assertThat(mpa).hasFieldOrPropertyWithValue("id", 1)
                );
    }

    @Test
    void getByIdNotValidEmptyTest() {
        assertThrows(NotFoundException.class, () -> mpaStorage.getById(10));
    }

    @Test
    void getByIdValidGenreTest() {
        Optional<Genre> genreOptional = Optional.ofNullable(genreStorage.getById(1));
        assertThat(genreOptional)
                .isPresent()
                .hasValueSatisfying(genre ->
                        assertThat(genre).hasFieldOrPropertyWithValue("id", 1)
                );
    }

    @Test
    void getByIdNotValidEmpty() {
        assertThrows(NotFoundException.class, () -> genreStorage.getById(10));
    }


    @Test
    void getByFilmIdGenresTest() {
        Film film = Film.builder()
                .name("name")
                .description("description")
                .duration(300)
                .releaseDate(LocalDate.ofEpochDay(1999 - 01 - 01))
                .mpa(Mpa.builder().id(1).build())
                .build();
        Integer filmId = filmStorage.create(film).getId();
        List<Genre> testGenres = genreStorage.getAll().subList(0, 5);
        genreStorage.addAllToFilmId(filmId, testGenres);
        List<Genre> genres = genreStorage.getByFilmId(filmId);
        assertThat(genres).hasSize(5).containsAll(testGenres);
    }
}