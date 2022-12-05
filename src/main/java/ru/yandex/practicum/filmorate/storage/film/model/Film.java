package ru.yandex.practicum.filmorate.storage.film.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.ReleaseDateValidation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
public class Film {
    private Integer id;
    @NotBlank(message = "Название не может быть пустым.")
    private final String name;
    @Size(min = 1, max = 200, message = "максимальная длина описания — 200 символов")
    private final String description;
    @ReleaseDateValidation(message = "дата не может быть раньше 28 декабря 1895 года")
    private final LocalDate releaseDate;
    @Positive(message = "длина фильма должна быть положительная.")
    private Integer duration;
    private final Mpa mpa;
    private final int likes;

    private Set<Genre> genres = new HashSet<>();

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("name", name);
        values.put("description", description);
        values.put("release_Date", releaseDate);
        values.put("duration", duration);
        values.put("mpa_id", mpa.getId());
        return values;
    }

    public Film(Integer id, String name, String description, LocalDate releaseDate, Integer duration,
                Mpa mpa, int likes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;
        this.likes = likes;
    }
}

