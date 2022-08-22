package com.example.filmorate.storage;

import com.example.filmorate.exception.FilmNotFoundException;
import com.example.filmorate.exception.NotFoundException;
import com.example.filmorate.model.Film;
import com.example.filmorate.model.Genre;
import com.example.filmorate.model.Mpa;
import com.example.filmorate.service.GenreService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Component
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final GenreService genreService;

    public FilmDbStorage(JdbcTemplate jdbcTemplate, GenreService genreService) {
        this.jdbcTemplate = jdbcTemplate;
        this.genreService = genreService;
    }

    @Override
    public Film create(Film film) {
        String sqlQuery = "INSERT INTO FILMS (FILM_NAME, FILM_RELEASE_DATE, FILM_DESCRIPTION, " +
                "FILM_DURATION, FILM_MPA) " +
                "VALUES (?, ?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sqlQuery, new String[]{"FILM_ID"});
            statement.setString(1, film.getName());
            statement.setDate(2, Date.valueOf(film.getReleaseDate()));
            statement.setString(3, film.getDescription());
            statement.setInt(4, film.getDuration());
            statement.setInt(5, film.getMpa().getId());
            return statement;
        }, keyHolder);
        film.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return film;
    }

    @Override
    public Film update(Film film) throws FilmNotFoundException {
        System.out.println("update");
        String sqlQuery = "UPDATE FILMS " +
                "SET FILM_NAME = ?, " +
                "FILM_RELEASE_DATE = ?," +
                "FILM_DESCRIPTION = ?, " +
                "FILM_DURATION = ?, " +
                "FILM_MPA = ? " +
                "WHERE FILM_ID = ?;";
        jdbcTemplate.update(
                sqlQuery,
                film.getName(),
                film.getReleaseDate(),
                film.getDescription(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId()
        );
        return getFilmById(film.getId());
    }

    @Override
    public Collection<Film> getFilms() {
        String sqlQuery =
                "SELECT f.FILM_ID, " +
                        "f.FILM_NAME, " +
                        "f.FILM_DESCRIPTION, " +
                        "f.FILM_RELEASE_DATE, " +
                        "f.FILM_DURATION, " +
                        "f.FILM_MPA, " +
                        "m.MPA_NAME AS mpa_name " +
                        "FROM films AS f " +
                        "JOIN MPA AS m" +
                        "    ON m.MPA_ID = f.FILM_MPA;";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> initFilm(rs, genreService));
    }

    @Override
    public Film getFilmById(int id) {
        String sqlQuery =
                "SELECT f.FILM_ID, " +
                        "f.FILM_NAME, " +
                        "f.FILM_RELEASE_DATE, " +
                        "f.FILM_DESCRIPTION, " +
                        "f.FILM_DURATION, " +
                        "f.FILM_MPA, " +
                        "m.MPA_NAME AS mpa_name " +
                        "FROM FILMS AS f " +
                        "JOIN MPA AS m" +
                        "    ON m.MPA_ID = f.FILM_MPA " +
                        "WHERE f.FILM_ID = ?;";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> initFilm(rs, genreService), id)
                .stream()
                .findAny()
                .orElseThrow(() -> new NotFoundException());
    }

    @Override
    public void deleteLike(Film film, int userId) {
        String sqlQuery = "DELETE FROM LIKES WHERE FILM_ID = ? AND USER_ID = ?;";
        jdbcTemplate.update(sqlQuery, film.getId(), userId);
    }

    @Override
    public void putLike(Film film, int userId) {
        String sqlQuery = "INSERT INTO LIKES (USER_ID, FILM_ID) VALUES (?, ?);";
        jdbcTemplate.update(sqlQuery, userId, film.getId());
    }

    @Override
    public List<Film> getByPopularity(int count) {
        String sqlQuery =
                "SELECT f.FILM_ID, " +
                        "f.FILM_NAME, " +
                        "f.FILM_DESCRIPTION, " +
                        "f.FILM_RELEASE_DATE, " +
                        "f.FILM_DURATION, " +
                        "f.FILM_MPA, " +
                        "m.MPA_NAME AS mpa_name " +
                        "FROM FILMS AS f " +
                        "JOIN MPA AS m" +
                        "    ON m.MPA_ID = f.FILM_MPA " +
                        "LEFT JOIN (SELECT FILM_ID, " +
                        "      COUNT(USER_ID) rate " +
                        "      FROM LIKES " +
                        "      GROUP BY film_id " +
                        ") r ON f.FILM_ID = r.FILM_ID " +
                        "ORDER BY r.rate DESC " +
                        "LIMIT ?;";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> initFilm(rs, genreService), count);
    }

    private Film initFilm(ResultSet rs, GenreService genreService) throws SQLException, NotFoundException {
        Integer id = rs.getInt("FILM_ID");
        String name = rs.getString("FILM_NAME");
        LocalDate releaseDate = rs.getDate("FILM_RELEASE_DATE").toLocalDate();
        String description = rs.getString("FILM_DESCRIPTION");
        Integer duration = rs.getInt("FILM_DURATION");
        List<Genre> genres = genreService.getByFilmId(id);
        Mpa mpa = new Mpa(
                rs.getInt("FILM_MPA"),
                rs.getString("MPA_NAME")
        );
        return new Film(id, name, releaseDate, description, duration, genres, mpa);
    }
}
