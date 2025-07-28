package ru.yandex.practicum.tarasov.transfer.client.blocker.dto;

public record BlockerResponseDto(boolean isAllowed,
                                 String reason,
                                 String errorMessage) {
}
