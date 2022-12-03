package ru.yandex.practicum.filmorate.storage.friend;

import ru.yandex.practicum.filmorate.models.User;

import java.util.List;

public interface FriendStorage {

    void addFriend(int userId, int friendId);

    void deleteFriend(int userId, int friendId);

    List<User> getFriends(Integer userId);

    List<User> getCommonsFriends(int user1Id, int user2Id);


}
