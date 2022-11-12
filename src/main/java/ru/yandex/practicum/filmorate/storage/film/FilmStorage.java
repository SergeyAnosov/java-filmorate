package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.models.Film;

import java.util.Collection;

public interface FilmStorage {

    Collection<Film> findAll();
    Film addFilm(Film film);

    Film updateFilm(Film film);

    Film deleteFilm(Film film);

    Film getById(int id);

}
