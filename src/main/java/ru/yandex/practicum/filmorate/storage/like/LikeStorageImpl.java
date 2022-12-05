package ru.yandex.practicum.filmorate.storage.like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;

import java.util.List;

@Repository
public class LikeStorageImpl implements LikeStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LikeStorageImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addLike(Integer filmId, Integer userId) {
        checkFilmId(filmId);
        checkUserId(userId);
        String sql = "INSERT INTO FILM_LIKES (FILM_ID, USER_ID) VALUES (?, ?)";
        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public void deleteLike(int filmId, int userId) {
        checkFilmId(filmId);
        checkUserId(userId);
        String sql = "DELETE FROM FILM_LIKES WHERE FILM_ID = ? AND USER_ID = ?";
        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public List<Integer> getLikes(int filmId) {
        checkFilmId(filmId);
        String sql = "SELECT USER_ID FROM FILM_LIKES WHERE FILM_ID = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("USER_ID"), filmId);
    }

    private void checkFilmId(int filmId) {
        String sqlQuery = "SELECT * FROM FILMS WHERE film_id = ?";
        SqlRowSet filmRow = jdbcTemplate.queryForRowSet(sqlQuery, filmId);
        if (!filmRow.next()) {
            throw new EntityNotFoundException("Фильм с id " + filmId + " не найден.");
        }
    }

    private void checkUserId(int userId) {
        String sqlQuery = "SELECT * FROM USERS WHERE user_id = ?";
        SqlRowSet userRow = jdbcTemplate.queryForRowSet(sqlQuery, userId);
        if (!userRow.next()) {
            throw new EntityNotFoundException("Пользователь с id " + userId + " не найден.");
        }
    }
}
