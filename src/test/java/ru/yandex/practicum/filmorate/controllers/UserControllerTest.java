package ru.yandex.practicum.filmorate.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.models.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserControllerTest {

    UserController userController = new UserController();

    @Test
    @DisplayName("Создаём юзера")
    public void createUser() {
        User sergey = new User("anosov@yandex.ru", "sergey", LocalDate.of(1988, 4, 3));
        userController.create(sergey);
        assertEquals(userController.getUsers().size(), 1);
        assertEquals(userController.findAll().stream().findFirst().get().getId(), 1);
        assertEquals(userController.findAll().stream().findFirst().get().getLogin(), "sergey");
        assertEquals(userController.findAll().stream().findFirst().get().getEmail(), "anosov@yandex.ru");
        assertEquals(userController.findAll().stream().findFirst().get().getName(), "sergey"); // ручная валидация
    }
}
