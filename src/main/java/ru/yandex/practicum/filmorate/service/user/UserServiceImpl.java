package ru.yandex.practicum.filmorate.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.storage.friend.FriendStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;
    private final FriendStorage friendStorage;

    @Autowired
    public UserServiceImpl(@Qualifier("userDbStorage") UserStorage userStorage, FriendStorage friendStorage) {
        this.userStorage = userStorage;
        this.friendStorage = friendStorage;
    }

    @Override
    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    @Override
    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    @Override
    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    @Override
    public User findUser(int id) {
        return userStorage.findById(id);
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
