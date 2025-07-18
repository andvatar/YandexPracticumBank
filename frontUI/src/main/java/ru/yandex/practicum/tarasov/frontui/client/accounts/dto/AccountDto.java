package ru.yandex.practicum.tarasov.frontui.client.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountDto {
    private long balance;
    private boolean exists;
    private Currency currency;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("balance", balance)
                .append("exists", exists)
                .append("currency", currency)
                .toString();
    }
}
