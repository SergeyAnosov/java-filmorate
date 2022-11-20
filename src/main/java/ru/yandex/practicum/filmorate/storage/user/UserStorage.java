package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.User;

import java.util.Collection;
import java.util.List;

public interface UserStorage {
    Collection<User> findAll();
    User createUser(User user);

    User updateUser(User user);

    User deleteUser(User user);

    User findById(Integer id);

}
