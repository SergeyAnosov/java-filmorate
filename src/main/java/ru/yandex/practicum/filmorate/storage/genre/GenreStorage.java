package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.storage.film.model.Film;
import ru.yandex.practicum.filmorate.storage.film.model.Genre;

import java.util.List;

public interface GenreStorage {

    Genre getGenreById(int id);

    List<Genre> getAllGenres();

    void putGenres(Film film);

    void deleteFilmGenre(Film film);

    void addFilmGenre(Film film);
}
