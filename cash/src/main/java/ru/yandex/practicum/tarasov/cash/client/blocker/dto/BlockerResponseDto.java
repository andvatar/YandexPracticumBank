package ru.yandex.practicum.tarasov.cash.client.blocker.dto;

public record BlockerResponseDto(boolean isAllowed,
                                 String reason,
                                 String errorMessage) {
}
