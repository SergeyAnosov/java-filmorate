package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.List;

public interface FilmServiceInterface {

    void addLike(int id1, int id2);

    void deleteLike(int id1, int id2);

    List<Film> getTopLikesFilms(int id);

    FilmStorage getFilmStorage();



}
