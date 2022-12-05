package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.storage.film.model.Film;
import ru.yandex.practicum.filmorate.storage.film.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;
import java.util.*;


@Slf4j
@Component("filmDbStorage")
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final MpaStorage mpaStorage;
    private final GenreStorage genreStorage;


    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate, MpaStorage mpaStorage, GenreStorage genreStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.mpaStorage = mpaStorage;
        this.genreStorage = genreStorage;

    }

    @Override
    public Film createFilm(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("FILMS")
                .usingGeneratedKeyColumns("FILM_ID");
        film.setId(simpleJdbcInsert.executeAndReturnKey(film.toMap()).intValue());
        genreStorage.addFilmGenre(film);
        log.info("Фильм добавлен");
        return getFilmById(film.getId());
    }

    @Override
    public List<Film> findAll() {
        String sql = "SELECT * FROM films";
        List<Film> allFilms = new ArrayList<>();
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet(sql);
        while (filmRows.next()) {
            allFilms.add(getFilmFromRow(filmRows));
        }
        return allFilms;
    }

    private Film getFilmFromRow(SqlRowSet rs) {
        Film film = new Film(
                rs.getInt("FILM_ID"),
                rs.getString("NAME"),
                rs.getString("DESCRIPTION"),
                rs.getDate("RELEASE_DATE").toLocalDate(),
                rs.getInt("DURATION"),
                mpaStorage.getMpaById(rs.getInt("MPA_ID")),
                getLikesAmount(rs.getInt("FILM_ID")));
        film.setGenres(getFilmGenres(film.getId()));
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (getSqlRowSetByFilmId(film.getId()).next()) {
            String sqlQuery = "UPDATE films SET " +
                    "name = ?, description = ?, release_date = ?, duration = ?, " +
                    "MPA_ID = ? WHERE FILM_ID = ?";
            jdbcTemplate.update(sqlQuery,
                    film.getName(),
                    film.getDescription(),
                    film.getReleaseDate(),
                    film.getDuration(),
                    film.getMpa().getId(),
                    film.getId());
            mpaStorage.getMpaById(film.getMpa().getId());
            removeFilmGenres(film.getId());
            addFilmGenres(film);
            film.setGenres(getFilmGenres(film.getId()));
            return film;
        } else {
            throw new EntityNotFoundException("Фильм с ID=" + film.getId() + " не найден!");
        }
    }

    @Override
    public void deleteFilm(int id) {
        if (id == 0) {
            throw new ValidationException("Передан пустой аргумент!");
        }
        String sqlQuery = "DELETE FROM films WHERE FILM_ID = ? ";
        if (jdbcTemplate.update(sqlQuery, id) == 0) {
            throw new EntityNotFoundException("Фильм с ID=" + id + " не найден!");
        }
    }

    @Override
    public Film getFilmById(int filmId) {

        String sql = "SELECT * FROM FILMS WHERE FILM_ID = ?";

        SqlRowSet filmRows = jdbcTemplate.queryForRowSet(sql, filmId);

        if (filmRows.next()) {
            Film film = getFilmFromRow(filmRows);
            log.info("Найден фильм: {}", film.getId());
            return film;
        } else {
            log.info("Фильм с идентификатором {} не найден.", filmId);
            throw new EntityNotFoundException("Данные не найдены");
        }
    }

    @Override
    public List<Film> getPopularFilms(int size) {
        List<Film> films = new ArrayList<>();

        SqlRowSet filmRows = jdbcTemplate.queryForRowSet("SELECT fm.*, COUNT(fl.ID) FROM FILMS AS fm " +
                "LEFT OUTER JOIN FILM_LIKES AS fl on fm.film_id = fl.film_id " +
                "GROUP BY fm.film_id ORDER BY COUNT(fl.ID) DESC LIMIT ?", size);
        while (filmRows.next()) {
            films.add(getFilmFromRow(filmRows));
        }
        return films;
    }

    private Set<Genre> getFilmGenres(int id) {
        Set<Genre> filmGenres = new TreeSet<>(Comparator.comparingInt(Genre::getId));
        String sqlQuery = "SELECT * FROM GENRES WHERE genre_id IN " +
                "(SELECT genre_id FROM FILM_GENRES WHERE film_id = ?)";
        SqlRowSet genreRows = jdbcTemplate.queryForRowSet(sqlQuery, id);
        while (genreRows.next()) {
            filmGenres.add(getGenreFromRow(genreRows));
        }
        return filmGenres;
    }

    private Genre getGenreFromRow(SqlRowSet genreRow) {
        return new Genre(genreRow.getInt("GENRE_ID"),
                genreRow.getString("NAME"));
    }

    private void removeFilmGenres(int id) {
        String sqlQuery = "DELETE FROM FILM_GENRES WHERE film_id = ?";
        jdbcTemplate.update(sqlQuery, id);
    }

    private SqlRowSet getSqlRowSetByFilmId(int id) {
        String sqlQuery = "SELECT * FROM FILMS WHERE film_id = ?";
        return jdbcTemplate.queryForRowSet(sqlQuery, id);
    }

    private void addFilmGenres(Film film) {
        checkGenre(film);
        String sqlQuery = "insert into FILM_GENRES(film_id, genre_id) values(?,?)";
        for (Genre genre : film.getGenres()) {
            jdbcTemplate.update(sqlQuery, film.getId(), genre.getId());
        }
    }

    private void checkGenre(Film film) {
        String sqlQuery = "SELECT * FROM GENRES WHERE GENRE_ID = ?";
        for (Genre genre : film.getGenres()) {
            if (!jdbcTemplate.queryForRowSet(sqlQuery, genre.getId()).next()) {
                throw new EntityNotFoundException("Жанр с id " + genre.getId() + " не найден.");
            }
        }
    }

    private int getLikesAmount(int id) {
        String sql = "SELECT COUNT(FILM_ID) AS amount FROM FILM_LIKES WHERE FILM_ID =?";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql, id);
        if (sqlRowSet.next()) {
            return sqlRowSet.getInt("amount");
        } else {
            return 0;
        }
    }
    
    @Override
    public List<Film> getCommonsFilms(int userId, int friendId) {
        checkUser(userId);
        checkUser(friendId);
        String sql = "SELECT film_id from film_likes WHERE user_id = ?
                        INTERSECT
                       SELECT film_id from film_likes WHERE user_id = ?";
        List<Film> commonFilms = new ArrayList<Film>():
        SqlRowSet rowSets = jdbcTemplate.queryForRowSet(sql, userId, friendId);
          if (rowSets.next()) {
            Film film = getFilmFromRow(rowSets);
            commonFilms.add(film);
          }
        return  commonFilms;
    }
    
    private void checkUser(int id) {
        String sql = "SELECT * FROM USERS WHERE USER_ID =?";
        SqlRowSet userRow = jdbcTemplate.queryForRowSet(sql, id);
        if (userRow.next()) {
            log.info("пользовтель с ID = " + id + " найден");
        } else {
            log.info("пользовтеля с ID = " + id + " не существует");
            throw new EntityNotFoundException("пользовтеля с ID = " + id + " не существует");
        }
    }
           
        
}
