package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.storage.film.model.Film;
import java.util.List;

public interface FilmStorage {

    List<Film> findAll();
    Film createFilm(Film film);

    Film updateFilm(Film film);

    void deleteFilm(int id);

    Film getFilmById(int id);

    List<Film> getPopularFilms(int size);
    
    List<Film> getCommonFilms(int userId, int friendId);

}
