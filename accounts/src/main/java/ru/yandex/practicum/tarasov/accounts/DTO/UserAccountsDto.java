package ru.yandex.practicum.tarasov.accounts.DTO;

import java.time.LocalDate;
import java.util.List;

public record UserAccountsDto(String username,
                              String lastName,
                              String firstName,
                              LocalDate birthDate,
                              List<AccountDto> accounts) {
}
