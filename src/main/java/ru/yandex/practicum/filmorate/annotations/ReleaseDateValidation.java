package ru.yandex.practicum.filmorate.annotations;

import ru.yandex.practicum.filmorate.annotations.validators.ReleaseDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ReleaseDateValidator.class)
public @interface ReleaseDateValidation {
    String message() default "error release date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
