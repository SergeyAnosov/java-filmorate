package ru.yandex.practicum.filmorate.storage.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.EntityNotFoundException;
import ru.yandex.practicum.filmorate.models.User;

import javax.validation.ValidationException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {

    private static final Logger log = LoggerFactory.getLogger(InMemoryUserStorage.class);
    private int id = 1;

    private final Map<Integer, User> users = new HashMap<>();


    @Override
    public Collection<User> findAll() {
        log.info("Получен запрос вывод всех пользователей ");
        return users.values();
    }

    @Override
    public User createUser(User user) {
        user.setId(id++);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        log.info("Получен запрос на внесение пользователя {} ", user);
        return user;
    }
    @Override
    public User updateUser(User user) {
        if (users.containsKey(user.getId())) {
            if (user.getName() == null || user.getName().isBlank()) {
                user.setName(user.getLogin());
            }
            users.put(user.getId(), user);
            log.info("Получен запрос на обновление пользователя {} ", user);
        } else {
            log.warn("Ошибка запроса на обновление пользователя {} ", user);
            throw new EntityNotFoundException("Юзер не найден");
        }
        return user;
    }

    @Override
    public User deleteUser(User user) {
        if (users.containsKey(user.getId())) {
            users.remove(user.getId(), user);
            log.info("Получен запрос на удаление пользователя {} ", user);
        } else {
            log.warn("Ошибка запроса на удаление пользователя {} ", user);
            throw new ValidationException("Ошибка при удалении пользователя");
        }
        return user;
    }

    @Override
    public User findById(Integer id) {
        if (users.containsKey(id)) {
            return users.get(id);
        } else {
            log.warn("Ошибка запроса на получение пользователя ");
            throw new EntityNotFoundException("Пользователя с таким id не существует");
        }
    }
}


