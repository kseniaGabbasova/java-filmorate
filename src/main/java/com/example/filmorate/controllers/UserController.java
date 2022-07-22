package com.example.filmorate.controllers;

import com.example.filmorate.exception.UserNotFoundException;
import com.example.filmorate.model.User;
import com.example.filmorate.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    private User create(@Validated @RequestBody User user) {
        return userService.create(user);
    }

    @PutMapping("/users")
    private User update(@Validated @RequestBody User user) throws UserNotFoundException {
        return userService.update(user);
    }

    @GetMapping("/users")
    private Collection<User> getUsers() {
        return userService.getUsers();
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    private void addFriend(@PathVariable int id, @PathVariable int friendId) throws UserNotFoundException {
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    private void deleteFriend(@PathVariable int id, @PathVariable int friendId) throws UserNotFoundException {
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/users/{id}/friends")
    private ArrayList<User> getFriends(@PathVariable int id) throws UserNotFoundException {
        return userService.getFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    private ArrayList<User> getCommonFriends(@PathVariable int id, @PathVariable int otherId)
            throws UserNotFoundException {
        return userService.getCommonFriends(id, otherId);
    }

    @GetMapping("/users/{id}")
    private User getUserById(@PathVariable int id) throws UserNotFoundException {
        return userService.getUserById(id);
    }
}
