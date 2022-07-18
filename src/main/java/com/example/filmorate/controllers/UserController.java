package com.example.filmorate.controllers;

import com.example.filmorate.controllers.exception.UserNotFoundException;
import com.example.filmorate.model.User;
import com.example.filmorate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/users")
    private User create(@Validated @RequestBody User user) {
        return userService.create(user);
    }

    @PutMapping("/users")
    private User update(@Validated @RequestBody User user) throws UserNotFoundException {
        User userUpdated = userService.update(user);
        if (userUpdated != null) {
            return userUpdated;
        } else {
            throw new UserNotFoundException();
        }
    }

    @GetMapping("/users")
    private Collection<User> getUsers() {
        return userService.getUsers();
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    private void addFriend(@PathVariable int id, @PathVariable int friendId) throws UserNotFoundException {
        boolean result = userService.addFriend(id, friendId);
        if (!result) {
            throw new UserNotFoundException();
        }
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    private void deleteFriend(@PathVariable int id, @PathVariable int friendId) throws UserNotFoundException {
        boolean result = userService.deleteFriend(id, friendId);
        if (!result) {
            throw new UserNotFoundException();
        }
    }

    @GetMapping("/users/{id}/friends")
    private ArrayList<User> getFriends(@PathVariable int id) throws UserNotFoundException {
        if (userService.getFriends(id) != null) {
            return userService.getFriends(id);
        } else {
            throw new UserNotFoundException();
        }
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    private ArrayList<User> getCommonFriends(@PathVariable int id, @PathVariable int otherId)
            throws UserNotFoundException {
        if (userService.getCommonFriends(id, otherId) != null) {
            return userService.getCommonFriends(id, otherId);
        } else {
            throw new UserNotFoundException();
        }
    }

    @GetMapping("/users/{id}")
    private User getUserById(@PathVariable int id) throws UserNotFoundException {
        if (userService.getUserById(id) != null) {
            return userService.getUserById(id);
        } else {
            throw new UserNotFoundException();
        }
    }
}
