package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.EntityNotFoundException;
import ru.yandex.practicum.filmorate.models.Mpa;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class MpaDbStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Mpa getMpaById(int id) {
        String sqlQuery = "SELECT * FROM RATINGS WHERE MPA_ID = ?";
        SqlRowSet mpaRow = jdbcTemplate.queryForRowSet(sqlQuery, id);
        if (mpaRow.next()) {
            return getMpaFromRow(mpaRow);
        } else {
            throw new EntityNotFoundException("MPA с id " + id + " не найден.");
        }
    }

    public List<Mpa> getAllMpa() {
        List<Mpa> allMpa = new ArrayList<>();
        String sqlQuery = "SELECT * FROM RATINGS";
        SqlRowSet mpaRow = jdbcTemplate.queryForRowSet(sqlQuery);
        while (mpaRow.next()) {
            allMpa.add(getMpaFromRow(mpaRow));
        }
        return allMpa;
    }

    private Mpa getMpaFromRow(SqlRowSet mpaRow) {
        return new Mpa(mpaRow.getInt("mpa_id"),
                        mpaRow.getString("name"));
    }


}


