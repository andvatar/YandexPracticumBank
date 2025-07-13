package ru.yandex.practicum.tarasov.accounts.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AgeValidator.class)
public @interface MinAge {
    String message() default "You must be at least {min} years old";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min() default 18;
}
