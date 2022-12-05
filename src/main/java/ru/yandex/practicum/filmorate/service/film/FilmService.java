package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.storage.film.model.Film;

import java.util.List;

public interface FilmService {
    public void addLike(int filmId, int userId);

    public Film createFilm(Film film);

    public List<Film> findAll();

    public Film getFilmById(int filmId);

    public Film updateFilm(Film film);

    public void deleteLike(int filmId, int userId);

    public List<Film> getTopLikesFilms(int size);
    
    List<Film> getCommonFilms(int id1, int id2);
}
