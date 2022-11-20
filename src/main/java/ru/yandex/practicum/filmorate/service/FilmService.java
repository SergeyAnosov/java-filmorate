package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.EntityNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.IncorrectParameterException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.like.LikeStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class FilmService {

    private final FilmStorage filmStorage;
    private final LikeStorage likeStorage;

    @Autowired

    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage, LikeStorage likeStorage) {
        this.filmStorage = filmStorage;
        this.likeStorage = likeStorage;
    }
    public void addLike(int filmId, int userId) {
       likeStorage.addLike(filmId,userId);
    }
    public Film createFilm(Film film) {
      return filmStorage.createFilm(film);
    }

    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film getFilmById(int filmId) {
        return filmStorage.getFilmById(filmId);
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }


    public void deleteLike(int filmId, int userId) {
        likeStorage.deleteLike(filmId,userId);
    }

    public List<Film> getTopLikesFilms(int size) {
        return filmStorage.getPopularFilms(size);
    }
}
