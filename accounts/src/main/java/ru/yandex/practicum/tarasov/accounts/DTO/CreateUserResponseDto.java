package ru.yandex.practicum.tarasov.accounts.DTO;

import java.time.LocalDateTime;
import java.util.List;

public record CreateUserResponseDto(LocalDateTime timeStamp,
                                    String status,
                                    List<String> errors) {
}
