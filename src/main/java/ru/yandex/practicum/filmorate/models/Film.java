package ru.yandex.practicum.filmorate.models;

import lombok.Data;
import ru.yandex.practicum.filmorate.annotations.ReleaseDateValidation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class  Film {
    private int id;
    @NotBlank(message = "Название не может быть пустым.")
    final String name;

    @Size(max = 200, message = "Длина не может быть больше 200 символов")
    private final String description;

    @ReleaseDateValidation(message = "дата не может быть раньше 28 декабря 1895 года")
    private final LocalDate releaseDate;

    @Positive(message = "длина фильма должна быть положительная.")
    private final Long duration;
}
