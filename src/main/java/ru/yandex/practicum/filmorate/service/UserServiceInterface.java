package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

public interface UserServiceInterface {

    void addFriend(int id1, int id2);

    void deleteFriend(int id1, int id2);

    List<User> getFriends(int id1);

    UserStorage getUserStorage();

    List<User> getCommonFriends(int id1, int id2);
}
