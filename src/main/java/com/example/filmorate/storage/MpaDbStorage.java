package com.example.filmorate.storage;

import com.example.filmorate.exception.NotFoundException;
import com.example.filmorate.model.Mpa;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class MpaDbStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;

    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Mpa> getAll() {
        String sqlQuery =
                "SELECT MPA_ID, " +
                        "MPA_NAME " +
                        "FROM MPA;";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> initMpa(rs));
    }

    @Override
    public Mpa getById(Integer id) throws NotFoundException {
        String sqlQuery =
                "SELECT MPA_ID, " +
                        "MPA_NAME " +
                        "FROM MPA " +
                        "WHERE MPA_ID = ?;";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> initMpa(rs), id)
                .stream()
                .findAny()
                .orElseThrow(() -> new NotFoundException());
    }

    private Mpa initMpa(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("MPA_ID");
        String name = rs.getString("MPA_NAME");
        return new Mpa(id, name);
    }
}