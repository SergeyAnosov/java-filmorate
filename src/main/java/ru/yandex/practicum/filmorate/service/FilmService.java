package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.EntityNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.IncorrectParameterException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private static final Logger log = LoggerFactory.getLogger(FilmService.class);

    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }
    public FilmStorage getFilmStorage() {
        return filmStorage;
    }

    public void addLike(int filmId, int userId) {
        if (filmStorage.findAll().contains(filmStorage.getById(filmId))) {
            if (filmStorage.getById(filmId) != null) {
                filmStorage.getById(filmId).getLikes().add(userId);
            }
        } else {
            throw new EntityNotFoundException("Такого фильма не существует");
        }
    }

    public void deleteLike(int filmId, int userId) {
        if (filmStorage.findAll().contains(filmStorage.getById(filmId))) {
            if (userId > 0) {
                filmStorage.getById(filmId).getLikes().remove(userId);
            } else {
                throw new IncorrectParameterException("userId");
            }
        } else {
            throw new EntityNotFoundException("Такого фильма не существует");
        }
    }

    public List<Film> getTopLikesFilms(int size) {
        return filmStorage.findAll()
                .stream()
                .sorted(Comparator.comparingInt(film -> -film.getLikes().size()))
                .limit(size)
                .collect(Collectors.toList());
    }
}
