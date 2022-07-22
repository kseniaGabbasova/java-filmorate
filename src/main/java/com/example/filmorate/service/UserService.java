package com.example.filmorate.service;

import com.example.filmorate.exception.UserNotFoundException;
import com.example.filmorate.model.User;
import com.example.filmorate.storage.UserStorage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserService {
    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User create(User user) {
        return userStorage.create(user);
    }

    public User update(User user) throws UserNotFoundException {
        if (userStorage.update(user) != null) {
            return userStorage.update(user);
        } else {
            throw new UserNotFoundException();
        }
    }

    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    public User getUserById(int id) throws UserNotFoundException {
        User user = userStorage.getUserById(id);
        if (user != null) {
            return userStorage.getUserById(id);
        } else {
            throw new UserNotFoundException();
        }
    }

    public void addFriend(int id, int otherId) throws UserNotFoundException {
        User user = userStorage.getUserById(id);
        User otherUser = userStorage.getUserById(otherId);
        if (user != null && otherUser != null) {
            userStorage.addFriend(user, otherUser);
        } else {
            throw new UserNotFoundException();
        }
    }

    public void deleteFriend(int id, int friendId) throws UserNotFoundException {
        User user = userStorage.getUserById(id);
        User friend = userStorage.getUserById(friendId);
        if (user != null && friend != null) {
            userStorage.deleteFriend(user, friend);
        } else {
            throw new UserNotFoundException();
        }
    }

    public ArrayList<User> getFriends(int id) throws UserNotFoundException {
        User user = userStorage.getUserById(id);
        if (user != null) {
            return userStorage.getFriends(user);
        } else {
            throw new UserNotFoundException();
        }
    }

    public ArrayList<User> getCommonFriends(int id, int otherId) throws UserNotFoundException {
        User user = userStorage.getUserById(id);
        User otherUser = userStorage.getUserById(otherId);
        if (user != null && otherUser != null) {
            return userStorage.getCommonFriends(user, otherUser);
        } else {
            throw new UserNotFoundException();
        }
    }
}
