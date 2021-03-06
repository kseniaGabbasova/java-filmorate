package com.example.filmorate.storage;

import com.example.filmorate.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private HashMap<Integer, User> users = new HashMap<>();


    public User create(User user) {
        user.setFriends(new HashSet<>());
        save(user);
        log.info("Добавлен пользователь: {}", user);
        return user;
    }

    public User update(User user) {
        if (users.containsKey(user.getId())) {
            user.setFriends(users.get(user.getId()).getFriends());
            users.replace(user.getId(), user);
            log.info("Обновлен пользователь: {}", user);
            return user;
        } else {
            return null;
        }
    }

    public Collection<User> getUsers() {
        return users.values();
    }

    public void addFriend(User user, User otherUser) {
        user.addFriend(otherUser.getId());
        otherUser.addFriend(user.getId());
        log.info("Пользователи добавлены в друзья друг другу: {}, {}", user, otherUser);
    }

    public User getUserById(int id) {
        return users.getOrDefault(id, null);
    }


    public void deleteFriend(User user, User friend) {
        user.getFriends().remove(friend.getId());
        friend.getFriends().remove(user.getId());
        log.info("Пользователи удалены из друзей друг друга: {}, {}", user, friend);
    }

    public ArrayList<User> getFriends(User user) {
        ArrayList<User> friends = new ArrayList<>();
        for (Integer i : user.getFriends()) {
            friends.add(users.get(i));
        }
        return friends;
    }

    public ArrayList<User> getCommonFriends(User user, User otherUser) {
        ArrayList<User> commonFriends = new ArrayList<>();
        Set<Integer> commonFriendsId = new HashSet<>(user.getFriends());
        if (user.getFriends().isEmpty() && otherUser.getFriends().isEmpty()) {
            return commonFriends;
        }
        commonFriendsId.retainAll(otherUser.getFriends());
        for (Integer i : commonFriendsId) {
            commonFriends.add(users.get(i));
        }
        return commonFriends;
    }

    private void save(User user) {
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
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
    }
}
