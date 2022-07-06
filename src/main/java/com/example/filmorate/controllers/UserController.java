package com.example.filmorate.controllers;

import com.example.filmorate.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;

@Slf4j
@RestController
public class UserController {
    private HashMap<Integer, User> users = new HashMap<>();

    @PostMapping("/users")
    private User create(@Validated @RequestBody User user) {
        save(user);
        return user;
    }

    @PutMapping("/users")
    private User update(@Validated @RequestBody User user) {
        if (users.containsKey(user.getId())) {
            users.replace(user.getId(), user);
            log.info("Пользователь обновлен");
            return user;
        } else {
            log.warn("Такого айди пользователя не существует");
            throw new ValidationException();
        }
    }

    @GetMapping("/users")
    private Collection<User> getUsers() {
        return users.values();
    }

    private void save(User user) {
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
            log.info("Имя пользователя пустое");
        }
        int max = 0;
        if (users.isEmpty()) {
            user.setId(1);
        }
        for (int i : users.keySet()) {
            if (i > max) {
                max = i;
            }
        }
        user.setId(max + 1);
        users.put(max + 1, user);
        log.info("Пользователь добавлен");
    }
}
