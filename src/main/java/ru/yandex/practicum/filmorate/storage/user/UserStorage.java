package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.User;

import java.util.Collection;

public interface UserStorage {
    Collection<User> findAll();
    User addUser(User user);

    User updateUser(User user);

    User deleteUser(User user);

    User findById(int id);

}
