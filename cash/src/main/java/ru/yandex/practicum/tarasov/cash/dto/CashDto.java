package ru.yandex.practicum.tarasov.cash.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CashDto {
    private String username;
    private String currency;
    private long value;
    private String action;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("username", username)
                .append("currency", currency)
                .append("value", value)
                .append("action", action)
                .toString();
    }
}
