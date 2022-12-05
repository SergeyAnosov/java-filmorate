/*

package ru.yandex.practicum.filmorate.storage.film;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.EntityNotFoundException;
import ru.yandex.practicum.filmorate.models.Film;

import javax.validation.ValidationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private static final Logger log = LoggerFactory.getLogger(InMemoryFilmStorage.class);
    private int id = 1;
    private final Map<Integer, Film> films = new HashMap<>();

    @Override
    public List<Film> findAll() {
        log.info("Получен запрос на вывод всех фильмов");
        return films.values();
    }

    @Override
    public Film addFilm(Film film) {
        film.setId(id++);
        films.put(film.getId(), film);
        log.info("Получен запрос на добавление фильма {} ", film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("Получен запрос на обновление фильма {} ", film);
        } else {
            throw new EntityNotFoundException("Такого фильма не существует");
        }
        return film;
    }

    @Override
    public Film deleteFilm(Film film) {
        if (films.containsKey(film.getId())) {
            films.remove(film.getId(), film);
            log.info("Получен запрос на удаление фильма {} ", film);
        } else {
            log.warn("Ошибка запроса удаления фильма {} ", film);
            throw new ValidationException("Фильма не существует");
        }
        return film;
    }

    @Override
    public Film getById(int id) {
        if (films.containsKey(id)) {
            return films.get(id);
        } else {
            log.warn("Ошибка получения фильма {} ", films.get(id));
            throw new EntityNotFoundException("Такого фильма не существует");
        }
    }
}

*/
