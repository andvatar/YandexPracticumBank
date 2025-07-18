package ru.yandex.practicum.tarasov.frontui.client.accounts.dto;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordDto(
        @NotBlank
        String login,
        @NotBlank
        String password,
        @NotBlank
        String confirmPassword) {
}
