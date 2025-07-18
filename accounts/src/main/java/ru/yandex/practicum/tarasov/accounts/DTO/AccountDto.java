package ru.yandex.practicum.tarasov.accounts.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.yandex.practicum.tarasov.accounts.entity.Currency;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountDto {
    private long balance;
    private boolean exists;
    private Currency currency;
}
