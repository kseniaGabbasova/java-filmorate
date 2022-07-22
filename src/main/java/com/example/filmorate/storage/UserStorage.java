package com.example.filmorate.storage;

import com.example.filmorate.model.User;

import java.util.ArrayList;
import java.util.Collection;

public interface UserStorage {
    User create(User user);

    User update(User user);

    Collection<User> getUsers();

    User getUserById(int id);

    void addFriend(User user, User otherUser);

    void deleteFriend(User user, User friend);

    ArrayList<User> getFriends(User user);

    ArrayList<User> getCommonFriends(User user, User otherUser);
}
