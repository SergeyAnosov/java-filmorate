package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.models.User;

import java.util.Collection;
import java.util.List;

public interface UserService {

    User createUser(User user);

    User updateUser(User user);

    Collection<User> findAll();

    User findUser(int id);

    void addFriend(int id1, int id2);

    void deleteFriend(int id1, int id2);

    List<User> getFriends(int id1);

    List<User> getCommonFriends(int id1, int id2);
}
