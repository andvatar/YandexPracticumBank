package ru.yandex.practicum.tarasov.frontui.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class AgeValidator implements ConstraintValidator<MinAge, LocalDate> {
    private int minAge;

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        if (date == null) {
            return false;
        }
        return Period.between(date, LocalDate.now()).getYears() >= minAge;
    }

    @Override
    public void initialize(MinAge constraintAnnotation) {
        this.minAge = constraintAnnotation.min();
    }
}
