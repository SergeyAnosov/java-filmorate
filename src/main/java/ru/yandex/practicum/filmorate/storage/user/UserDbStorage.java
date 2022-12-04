package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.storage.film.model.User;

import javax.validation.ValidationException;
import java.util.List;


@Slf4j
@Repository
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new User(
                rs.getInt("USER_ID"),
                rs.getString("EMAIL"),
                rs.getString("LOGIN"),
                rs.getString("NAME"),
                rs.getDate("BIRTHDAY").toLocalDate(),
                null)
        );
    }

    @Override
    public User createUser(User user) {
        if (user == null) {
            throw new ValidationException("ошибка");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("USERS")
                .usingGeneratedKeyColumns("USER_ID");
        user.setId(simpleJdbcInsert.executeAndReturnKey(user.toMap()).intValue());
        log.info("создан пользователь c ID={}", user.getId());
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (user.getId() == null) {
            throw new ru.yandex.practicum.filmorate.exception.ValidationException("Передан пустой аргумент!");
        }
        if (findById(user.getId()) != null) {
            String sqlQuery = "UPDATE users SET " +
                    "email = ?, login = ?, name = ?, birthday = ? " +
                    "WHERE USER_ID = ?";
            jdbcTemplate.update(sqlQuery,
                    user.getEmail(),
                    user.getLogin(),
                    user.getName(),
                    user.getBirthday(),
                    user.getId());
            log.info("Пользователь с ID={} успешно обновлен", user.getId());
            return user;
        } else {
            throw new EntityNotFoundException("Пользователь с ID=" + user.getId() + " не найден!");
        }
    }

    @Override
    public User deleteUser(User user) {
        if (user == null) {
            throw new ValidationException("ошибка валдиации");
        }
        if (findById(user.getId()) != null) {
            String sqlQuery = "delete from USERS where USER_ID = ?";
            jdbcTemplate.update(sqlQuery, user.getId());
        }
        log.info("Удалён пользователь с ID={}", user.getId());
        return user;
    }

    @Override
    public User findById(Integer id) {
        if (id == null) {
            throw new ru.yandex.practicum.filmorate.exception.ValidationException("Передан пустой аргумент!");
        }
        User user;
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("SELECT * FROM users WHERE USER_ID = ?", id);
        if (userRows.first()) {
            user = new User(
                    userRows.getInt("USER_ID"),
                    userRows.getString("email"),
                    userRows.getString("login"),
                    userRows.getString("name"),
                    userRows.getDate("birthday").toLocalDate(),
                    null);
        } else {
            throw new EntityNotFoundException("Пользователь с ID=" + id + " не найден!");
        }
        return user;
    }
}
