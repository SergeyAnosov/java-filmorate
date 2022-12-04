package ru.yandex.practicum.filmorate.service.mpa;

import ru.yandex.practicum.filmorate.storage.film.model.Mpa;

import java.util.List;

public interface MpaService {
    List<Mpa> getAllMpa();

    Mpa getMpaById(int id);
}
