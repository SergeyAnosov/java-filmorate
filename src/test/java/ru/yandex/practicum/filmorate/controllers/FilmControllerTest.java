package ru.yandex.practicum.filmorate.controllers;

import org.junit.Assert;
import org.junit.jupiter.api.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class FilmControllerTest {

    FilmController filmController = new FilmController();

    @AfterEach
    public void clear() {
        filmController.getFilms().clear();


    }

    @Test
    @DisplayName("Сохраняем и проверяем корректный фильм")
    public void lookAtFilm() {
        Film film1 = new Film("Titanik", "romantic", LocalDate.of(1990, 5, 10), 180L); // корректный фильм
        filmController.create(film1);
        assertEquals(filmController.getAll().size(), 1);
        for (Film film : filmController.getAll()) {
            assertEquals(film.getId(), 1);
            assertEquals(film.getName(), "Titanik");
            assertEquals(film.getDescription(), "romantic");
            assertEquals(film.getReleaseDate(), LocalDate.of(1990, 5, 10));
            assertEquals(film.getDuration(), 180L);
        }
    }

}
