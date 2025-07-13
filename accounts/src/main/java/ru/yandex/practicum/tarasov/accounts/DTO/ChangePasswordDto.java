package ru.yandex.practicum.tarasov.accounts.DTO;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record ChangePasswordDto(
        @NotBlank
        String login,
        @NotBlank
        String password,
        @NotBlank
        String confirmPassword) {
}
