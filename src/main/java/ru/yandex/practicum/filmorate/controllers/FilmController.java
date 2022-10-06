package ru.yandex.practicum.filmorate.controllers;

import org.springframework.http.HttpStatus;
import ru.yandex.practicum.filmorate.models.Film;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController {
    private int id = 1;

    private final Map<Integer, Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> findAll() {
        return films.values();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film create(@RequestBody Film film) {
        film.setId(id++);
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film put(@RequestBody Film film) {
        films.put(film.getId(), film);
        return film;
    }


}
