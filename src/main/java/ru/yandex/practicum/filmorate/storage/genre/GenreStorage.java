package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.Genre;

import java.util.List;

public interface GenreStorage {

    Genre getGenreById(int id);

    List<Genre> getAllGenres();

    void putGenres(Film film);

    void deleteFilmGenre(Film film);

    void addFilmGenre(Film film);
}
