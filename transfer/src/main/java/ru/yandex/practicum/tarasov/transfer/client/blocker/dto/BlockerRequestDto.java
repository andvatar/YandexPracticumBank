package ru.yandex.practicum.tarasov.transfer.client.blocker.dto;

public record BlockerRequestDto(String fromUser,
                                String toUser,
                                String fromAccount,
                                String toAccount,
                                String action,
                                long amount) {
}
