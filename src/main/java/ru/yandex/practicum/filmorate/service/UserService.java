package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.EntityNotFoundException;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserServiceInterface {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public UserStorage getUserStorage() {
        return userStorage;
    }

    @Override
    public void addFriend(int user1Id, int user2Id) {
        if (!userStorage.findAll().contains(userStorage.findById(user1Id))
                || !userStorage.findAll().contains(userStorage.findById(user2Id))) {
            throw new EntityNotFoundException("Одного из пользователей не существует");
        }
        if ((userStorage.findById(user1Id) != null) && (userStorage.findById(user2Id) != null)) {
            userStorage.findById(user1Id).getFriends().add(user2Id);
            userStorage.findById(user2Id).getFriends().add(user1Id);
        }
    }

    @Override
    public void deleteFriend(int user1Id, int user2Id) {
        if (!userStorage.findAll().contains(userStorage.findById(user1Id))
                || !userStorage.findAll().contains(userStorage.findById(user2Id))) {
            throw new EntityNotFoundException("Одного из пользователей не существует");
        }
        if ((userStorage.findById(user1Id) != null) && (userStorage.findById(user2Id) != null)) {
            userStorage.findById(user1Id).getFriends().remove(user2Id);
            userStorage.findById(user2Id).getFriends().remove(user1Id);
        }
    }

    public List<User> getFriends(int id) {
        if (userStorage.findAll().contains(userStorage.findById(id))) {
            User user = userStorage.findById(id);
            List<User> users = new ArrayList<>();
            for (Integer i : user.getFriends()) {
                User us = userStorage.findById(i);
                users.add(us);
            }
            return users;
        } throw new EntityNotFoundException("Пользователя не существует");
    }

    @Override
    public List<User> getCommonFriends(int id1, int id2) {
        if (userStorage.findAll().contains(userStorage.findById(id1))
                || userStorage.findAll().contains(userStorage.findById(id2))) {
            return getFriends(id1).stream()
                    .filter(x -> getFriends(id2).stream().anyMatch(y -> y == x))
                    .collect(Collectors.toList());
        } else {
            throw new EntityNotFoundException("Одного из пользователей не существует");
        }
    }
}
