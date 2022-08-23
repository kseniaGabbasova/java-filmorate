package com.example.filmorate.storage;

import com.example.filmorate.exception.UserNotFoundException;
import com.example.filmorate.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface UserStorage {
    User create(User user);

    User update(User user) throws UserNotFoundException;

    Collection<User> getUsers();

    User getUserById(int id) throws UserNotFoundException;

    void addFriend(User user, User otherUser);

    void deleteFriend(User user, User friend);

    List<Integer> getFriends(int id);

    ArrayList<User> getCommonFriends(User user, User otherUser) throws UserNotFoundException;
}
