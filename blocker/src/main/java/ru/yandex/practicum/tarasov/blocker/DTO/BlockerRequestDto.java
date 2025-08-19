package ru.yandex.practicum.tarasov.blocker.DTO;

public record BlockerRequestDto(String fromUser,
                                String toUser,
                                String fromAccount,
                                String toAccount,
                                String action,
                                long amount) {
}
