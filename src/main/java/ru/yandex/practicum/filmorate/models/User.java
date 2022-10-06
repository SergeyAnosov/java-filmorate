package ru.yandex.practicum.filmorate.models;

import lombok.Data;

import java.time.LocalDate;

@Data

public class User {
    private final int id;
    private final String email;
    private final String login;
    private String name;
    private final LocalDate birthday;
}
