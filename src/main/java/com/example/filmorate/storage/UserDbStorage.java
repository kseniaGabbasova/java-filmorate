package com.example.filmorate.storage;

import com.example.filmorate.exception.UserNotFoundException;
import com.example.filmorate.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Component
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User create(User user) {
        String sqlQuery = "insert into USERS(USER_LOGIN, USER_NAME, USER_EMAIL, USER_BIRTHDAY) " +
                "values(?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        if(user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        jdbcTemplate.update(con -> {
            PreparedStatement stmt = con.prepareStatement(sqlQuery, new String[]{"USER_ID"});
            stmt.setString(3, user.getEmail());
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getName());
            stmt.setDate(4, Date.valueOf(user.getBirthday()));
            return stmt;
        }, keyHolder);
        user.setId(keyHolder.getKey().intValue());
        return user;
    }

    @Override
    public User update(User user) throws UserNotFoundException {
        String sqlQuery = "UPDATE users " +
                "SET USER_LOGIN = ?, USER_NAME = ?, USER_EMAIL = ?, USER_BIRTHDAY = ? " +
                "WHERE USER_ID = ?;";
        jdbcTemplate.update(
                sqlQuery,
                user.getLogin(),
                user.getName(),
                user.getEmail(),
                user.getBirthday(),
                user.getId()
        );
        return getUserById(user.getId());
    }

    @Override
    public Collection<User> getUsers() {
        String sqlQuery =
                "SELECT * FROM USERS";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> initUser(rs));
    }

    @Override
    public User getUserById(int id) throws UserNotFoundException {
        String sqlQuery = "select * FROM USERS where USER_ID=?";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> initUser(rs), id)
                .stream()
                .findAny().orElseThrow(() -> new UserNotFoundException());
        }

    @Override
    public void addFriend(User user, User otherUser) {
        String sqlQuery = "INSERT INTO FRIENDS (user_id, friend_id) VALUES (?, ?);";
        jdbcTemplate.update(sqlQuery, user.getId(), otherUser.getId());
    }

    @Override
    public void deleteFriend(User user, User friend) {
        String sqlQuery = "DELETE FROM FRIENDS WHERE user_id = ? AND friend_id = ?;";
        jdbcTemplate.update(sqlQuery, user.getId(), friend.getId());
    }

    @Override
    public List<Integer> getFriends(int id) {
        String sqlQuery =
                "SELECT friend_id FROM FRIENDS WHERE user_id = ?;";
        return jdbcTemplate.queryForList(sqlQuery, Integer.class, id);


    }

    @Override
    public ArrayList<User> getCommonFriends(User user, User otherUser) throws UserNotFoundException {
        ArrayList<User> commonFriends = new ArrayList<>();
        Set<Integer> commonFriendsId = new HashSet<>(getFriends(user.getId()));
        if (commonFriendsId.isEmpty() && getFriends(otherUser.getId()).isEmpty()) {
            return commonFriends;
        }
        commonFriendsId.retainAll(getFriends(otherUser.getId()));
        for (Integer i : commonFriendsId) {
            commonFriends.add(getUserById(i));
        }

        return commonFriends;
    }

    private User initUser(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("USER_ID");
        String email = rs.getString("USER_EMAIL");
        String login = rs.getString("USER_LOGIN");
        String name = rs.getString("USER_NAME");
        LocalDate birthday = rs.getDate("USER_BIRTHDAY").toLocalDate();
        System.out.println(new User(id, email, login, name, birthday));
        return new User(id, email, login, name, birthday);

    }
}

