package com.example.filmorate.storage;

import com.example.filmorate.exception.NotFoundException;
import com.example.filmorate.model.Genre;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> getAll() {
        String sqlQuery =
                "SELECT GENRE_ID, " +
                        "GENRE_NAME " +
                        "FROM GENRES;";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> initGenre(rs));
    }

    @Override
    public Genre getById(int id) throws NotFoundException {
        String sqlQuery =
                "SELECT GENRE_ID, " +
                        "GENRE_NAME " +
                        "FROM GENRES " +
                        "WHERE GENRE_ID = ?;";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> initGenre(rs), id)
                .stream()
                .findAny()
                .orElseThrow(() -> new NotFoundException());
    }

    @Override
    public List<Genre> getByFilmId(int filmId) throws NotFoundException {
        String sqlQuery =
                "SELECT g.GENRE_ID, " +
                        "g.GENRE_NAME " +
                        "FROM FILM_GENRES AS fg " +
                        "JOIN GENRES AS g ON fg.GENRE_ID = g.GENRE_ID " +
                        "WHERE fg.FILM_ID = ?;";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> initGenre(rs), filmId);
    }

    @Override
    public void addAllToFilmId(int filmId, List<Genre> genres) {
        List<Genre> genresDistinct = genres.stream().distinct().collect(Collectors.toList());
        jdbcTemplate.batchUpdate(
                "INSERT INTO FILM_GENRES (GENRE_ID, FILM_ID) VALUES (?, ?);",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement statement, int i) throws SQLException {
                        statement.setInt(1, genresDistinct.get(i).getId());
                        statement.setInt(2, filmId);
                    }

                    public int getBatchSize() {
                        return genresDistinct.size();
                    }
                }
        );
    }

    @Override
    public void deleteAllByFilmId(int filmId) {
        String sqlQuery = "DELETE FROM FILM_GENRES WHERE FILM_ID = ?;";
        jdbcTemplate.update(sqlQuery, filmId);
    }

    private Genre initGenre(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("GENRE_ID");
        String name = rs.getString("GENRE_NAME");
        return new Genre(id, name);
    }
}