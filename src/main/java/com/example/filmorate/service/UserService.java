package com.example.filmorate.service;

import com.example.filmorate.model.User;
import com.example.filmorate.storage.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserService {
    @Autowired
    UserStorage userStorage;

    public User create(User user) {
        return userStorage.create(user);
    }

    public User update(User user) {
        return userStorage.update(user);
    }

    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    public User getUserById(int id) {
        return userStorage.getUserById(id);
    }

    public boolean addFriend(int id, int otherId) {
        User user = userStorage.getUserById(id);
        User otherUser = userStorage.getUserById(otherId);
        if (user != null && otherUser != null) {
            userStorage.addFriend(user, otherUser);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteFriend(int id, int friendId) {
        User user = userStorage.getUserById(id);
        User friend = userStorage.getUserById(friendId);
        if (user != null && friend != null) {
            userStorage.deleteFriend(user, friend);
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<User> getFriends(int id) {
        User user = userStorage.getUserById(id);
        if (user != null) {
            return userStorage.getFriends(user);
        } else {
            return null;
        }
    }

    public ArrayList<User> getCommonFriends(int id, int otherId) {
        User user = userStorage.getUserById(id);
        User otherUser = userStorage.getUserById(otherId);
        if (user != null && otherUser != null) {
            return userStorage.getCommonFriends(user, otherUser);
        } else {
            return null;
        }
    }
}
