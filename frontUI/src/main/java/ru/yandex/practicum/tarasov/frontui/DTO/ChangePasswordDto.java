package ru.yandex.practicum.tarasov.frontui.DTO;

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
