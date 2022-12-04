package ru.yandex.practicum.filmorate.service.genre;

import ru.yandex.practicum.filmorate.storage.film.model.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> getAllGenres();

    Genre getGenreById(int id);
}
