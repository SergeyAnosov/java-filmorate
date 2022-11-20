package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.EntityNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.storage.friend.FriendStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserServiceInterface {

    private final UserStorage userStorage;
    private FriendStorage friendStorage;


    @Autowired
    public UserService(@Qualifier("userDbStorage") UserStorage userStorage, FriendStorage friendStorage) {
        this.userStorage = userStorage;
        this.friendStorage = friendStorage;
    }

    @Override
    public UserStorage getUserStorage() {
        return userStorage;
    }

    @Override
    public void addFriend(int user1Id, int user2Id) {
        friendStorage.addFriend(user1Id, user2Id);
    }

    @Override
    public void deleteFriend(int user1Id, int user2Id) {
        friendStorage.deleteFriend(user1Id, user2Id);
    }

    @Override
    public List<User> getFriends(int id) {
        return friendStorage.getFriends(id);
    }

    @Override
    public List<User> getCommonFriends(int id1, int id2) {
        return friendStorage.getCommonsFriends(id1, id2);
    }
}
