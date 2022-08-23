package com.example.filmorate;


import com.example.filmorate.exception.UserNotFoundException;
import com.example.filmorate.model.User;
import com.example.filmorate.storage.UserDbStorage;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserTest {
    private final UserDbStorage userStorage;

    @Test
    void getByIdValidUserTest() throws UserNotFoundException {
        User testUser = User.builder()
                .email("name@ya.ru")
                .login("login")
                .name("name")
                .birthday(LocalDate.ofEpochDay(1999 - 01 - 01))
                .build();

        Integer userId = userStorage.create(testUser).getId();

        Optional<User> userOptional = Optional.ofNullable(userStorage.getUserById(userId));
        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", userId)
                );
    }

    @Test
    void getByIdNotValid() {
        assertThrows(UserNotFoundException.class, () -> userStorage.getUserById(100));
    }
}
