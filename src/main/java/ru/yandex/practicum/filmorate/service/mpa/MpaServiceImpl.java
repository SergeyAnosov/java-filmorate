package ru.yandex.practicum.filmorate.service.mpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.storage.film.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.ArrayList;
import java.util.List;

@Service
public class MpaServiceImpl implements MpaService {
    private final MpaStorage mpaStorage;

    @Autowired
    public MpaServiceImpl(MpaStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    @Override
    public List<Mpa> getAllMpa() {
        return new ArrayList<>(mpaStorage.getAllMpa());
    }

    @Override
    public Mpa getMpaById(int id) {
        return mpaStorage.getMpaById(id);
    }

}
