package ru.yandex.practicum.tarasov.blocker.DTO;

public record BlockerResponseDto(boolean isAllowed,
                                 String reason,
                                 String errorMessage) {
}
