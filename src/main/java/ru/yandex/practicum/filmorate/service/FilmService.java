package ru.yandex.practicum.filmorate.service;

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

public class FilmService implements FilmServiceInterface {

    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }
    @Override
    public FilmStorage getFilmStorage() {
        return filmStorage;
    }


    @Override
    public void addLike(int filmId, int userId) {
        if (!filmStorage.findAll().contains(filmStorage.getById(filmId))) {
            throw new EntityNotFoundException("Такого фильма c id " + filmId + " не существует");
        }
        if (filmStorage.getById(filmId) != null) {
            filmStorage.getById(filmId).getLikes().add(userId);
        }
    }

    @Override
    public void deleteLike(int filmId, int userId) {
        if (!filmStorage.findAll().contains(filmStorage.getById(filmId))) {
            throw new EntityNotFoundException("Такого фильма c id " + filmId + " не существует");
        }
        if (userId <= 0) {
            throw new IncorrectParameterException("userId");
        }
        filmStorage.getById(filmId).getLikes().remove(userId);
    }

    public List<Film> getTopLikesFilms(int size) {
        return filmStorage.findAll()
                .stream()
                .sorted(Comparator.comparingInt(film -> -film.getLikes().size()))
                .limit(size)
                .collect(Collectors.toList());
    }
}
