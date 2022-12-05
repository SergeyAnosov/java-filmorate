package ru.yandex.practicum.filmorate.storage.mpa;

import ru.yandex.practicum.filmorate.storage.film.model.Mpa;

import java.util.List;

public interface MpaStorage {

    Mpa getMpaById(int id);

    List<Mpa> getAllMpa();

}
