/*
package ru.yandex.practicum.filmorate.controllers;
import org.junit.jupiter.api.*;
import ru.yandex.practicum.filmorate.models.Film;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class FilmControllerTest {

    FilmController filmController = new FilmController();

    @Test
    @DisplayName("Сохраняем и проверяем корректный фильм")
    public void createAndUpdateFilm () {
        Film testFilm = new Film("Titanic", "romantic", LocalDate.of(1990, 5, 10), 180L); // корректный фильм
        filmController.create(testFilm);
        assertEquals(filmController.getAll().size(), 1);
        for (Film film : filmController.getAll()) {
            assertEquals(film.getId(), 1);
            assertEquals(film.getName(), "Titanic");
            assertEquals(film.getDescription(), "romantic");
            assertEquals(film.getReleaseDate(), LocalDate.of(1990, 5, 10));
            assertEquals(film.getDuration(), 180L);
        }

        Film updateFilm = new Film("Titanic2", "drama", LocalDate.of(1995, 10, 12), 240L);
        updateFilm.setId(1);
        assertEquals(updateFilm.getId(), 1);
        assertEquals(updateFilm.getName(), "Titanic2");
        assertEquals(updateFilm.getDescription(), "drama");
        assertEquals(updateFilm.getReleaseDate(), LocalDate.of(1995, 10, 12));
        assertEquals(updateFilm.getDuration(), 240L);
    }
}
*/
