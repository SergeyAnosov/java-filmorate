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

    @Test
    @DisplayName("Обновляем фильм")
    public void updateFilm() {
        Film newFilm = new Film ("Titanik2", "romantic", LocalDate.of(2022, 10, 10), 120L);
        newFilm.setId(1);
        filmController.put(newFilm);
        assertEquals(newFilm, filmController.getAll().stream().findFirst().get());
    }

    @Test
    @DisplayName("Создаём не валидные фильмы") // releasedDate сделана через кастомную анотацию. Она не проверяется
    public void badFilms() {
        Film badFilm1 = new Film("   ", "comedian", LocalDate.of(1990, 5, 10), 200L); // не корректное имя
        Film badFilm2 = new Film("dfhsjdfh", "comedian", LocalDate.of(1990, 5, 10), -200L); // не корректная продолжительность
        Film badFilm3 = new Film("Властелин колец", "123456789011121314151617181920212" +
                "2232425262728293031323334353637383940414243444546474849505152535455565758596" +
                "0616263646566676869707172737475767778798081828384858687888990919293949596979899100", LocalDate.of(2002, 5, 10), 200L); // не корректное описание

        filmController.create(badFilm1);
        filmController.create(badFilm2);
        filmController.create(badFilm3);

        try {
            filmController.create(badFilm1);
        }catch (ValidationException e){
            assertNotEquals("",e.getMessage());
            System.out.println(e.getMessage());
        }
        try {
            filmController.create(badFilm2);
        }catch (ValidationException e){
            assertNotEquals("",e.getMessage());
            System.out.println(e.getMessage());
        }
        try {
            filmController.create(badFilm3);
        }catch (ValidationException e){
            assertNotEquals("",e.getMessage());
            System.out.println(e.getMessage());
        }

    }


}
