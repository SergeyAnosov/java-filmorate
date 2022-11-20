package ru.yandex.practicum.filmorate.storage.friend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.EntityNotFoundException;
import ru.yandex.practicum.filmorate.models.User;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class FriendStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FriendStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addFriend(int userId, int friendId) {
        checkUser(userId);
        checkUser(friendId);
        String sql = "INSERT INTO FRIENDS(USER_ID, FRIEND_ID) VALUES ( ?, ? )";
        jdbcTemplate.update(sql, userId, friendId);
        log.info("Пользователю с id=" + userId + " добавлен друг с id=" + friendId);

    }

    public void deleteFriend(int userId, int friendId) {
        checkUser(userId);
        checkUser(friendId);
        String sql = "DELETE FROM FRIENDS WHERE USER_ID = ? AND FRIEND_ID = ?";
        jdbcTemplate.update(sql, userId, friendId);
        log.info("У пользователя с id=" + userId + " удалён друг с id=" + friendId);
    }

    public List<User> getFriends(Integer userId) {
        checkUser(userId);
        String sql = "SELECT USERS.* FROM friends" +
                    " INNER JOIN USERS ON FRIENDS.FRIEND_ID = USERS.USER_ID WHERE FRIENDS.USER_ID = ?";
            return jdbcTemplate.query(sql, (rs, rowNum) -> new User(
                            rs.getInt("USER_ID"),
                            rs.getString("EMAIL"),
                            rs.getString("LOGIN"),
                            rs.getString("NAME"),
                            rs.getDate("BIRTHDAY").toLocalDate(),
                            null),
                    userId
            );
    }

    private void checkUser(int id) {
        String sql = "SELECT * FROM USERS WHERE USER_ID =?";
        SqlRowSet userRow = jdbcTemplate.queryForRowSet(sql, id);
        if (userRow.next()) {
            log.info("пользовтель с ID = " + id + " найден");
        } else {
            log.info("пользовтеля с ID = " + id + " не существует");
            throw new EntityNotFoundException("пользовтеля с ID = "  + id + " не существует");
        }
    }

    public List<User> getCommonsFriends(int user1Id, int user2Id) {
        checkUser(user1Id);
        checkUser(user2Id);
        String sql =   " SELECT *" +
                        " FROM USERS " +
                        " WHERE user_id IN (" +
                        " (SELECT f1.friend_id " +
                        " FROM (SELECT user_id, friend_id  " +
                        " FROM friends " +
                        " WHERE user_id = ?) AS f1 " +
                        " INNER JOIN " +
                        " (SELECT user_id, friend_id " +
                        " FROM friends " +
                        " WHERE user_id = ?) as f3 " +
                        " ON f1.friend_id = f3.friend_id)) ";

        /*SqlRowSet userRows = jdbcTemplate.queryForRowSet("select m.* from users_friends u" +
                " inner join users_friends f on u.user_friend_id = f.user_friend_id inner join users_model m" +
                " on m.user_id = u.user_friend_id where u.user_id = ? and f.user_id = ?", userId, otherId);*/

        return jdbcTemplate.query(sql, (rs, rowNum) -> new User(
                        rs.getInt("USER_ID"),
                        rs.getString("EMAIL"),
                        rs.getString("LOGIN"),
                        rs.getString("NAME"),
                        rs.getDate("BIRTHDAY").toLocalDate(),
                        null),
                user1Id, user2Id);
    }
   /* private List<User> getUsersFromDb(SqlRowSet userRows) {
        List<User> usersFromDb = new ArrayList<>();
        while (userRows.next()) {
            User dbUser = new User(userRows.getString("email"),
                    userRows.getString("login"), userRows.getString("name"),
                    userRows.getDate("birthday").toLocalDate());
            dbUser.setId(userRows.getInt("user_id"));
            usersFromDb.add(dbUser);
        }
        return usersFromDb;
    }*/
}
