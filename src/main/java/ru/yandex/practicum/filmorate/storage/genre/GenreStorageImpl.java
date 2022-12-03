package ru.yandex.practicum.filmorate.storage.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.EntityNotFoundException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.Genre;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GenreStorageImpl implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreStorageImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre getGenreById(int id) {
        SqlRowSet mpaRows = jdbcTemplate.queryForRowSet("SELECT * FROM GENRES WHERE GENRE_ID = ?", id);

        if (mpaRows.next()) {
            return new Genre(
                    mpaRows.getInt("genre_id"),
                    mpaRows.getString("name")
            );
        } else {
            throw new EntityNotFoundException("Данные не найдены");
        }
    }

    @Override
    public List<Genre> getAllGenres() {
        List<Genre> genres = new ArrayList<>();
        SqlRowSet mpaRows = jdbcTemplate.queryForRowSet("SELECT * FROM GENRES");
        while (mpaRows.next()) {
            Genre genre = new Genre(
                    mpaRows.getInt("genre_id"),
                    mpaRows.getString("name")
            );
            genres.add(genre);
        }
        return genres;
    }

    @Override
    public void putGenres(Film film) {
        deleteFilmGenre(film);
        addFilmGenre(film);
    }

    @Override
    public void deleteFilmGenre(Film film) {
        jdbcTemplate.update("DELETE FROM FILM_GENRES WHERE film_id = ?", film.getId());
    }

    @Override
    public void addFilmGenre(Film film) {
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                jdbcTemplate.update("INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?)",
                        film.getId(), genre.getId());
            }
        }
    }


}
