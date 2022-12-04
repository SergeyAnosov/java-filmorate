package ru.yandex.practicum.filmorate.service.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.storage.film.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreStorage genreStorage;

    @Autowired
    public GenreServiceImpl(GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    @Override
    public List<Genre> getAllGenres() {
        return new ArrayList<>(genreStorage.getAllGenres());
    }

    @Override
    public Genre getGenreById(int id) {
        return genreStorage.getGenreById(id);
    }
}
