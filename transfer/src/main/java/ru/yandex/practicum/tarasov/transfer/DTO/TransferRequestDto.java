package ru.yandex.practicum.tarasov.transfer.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TransferRequestDto {
    private String fromLogin;
    private String fromCurrency;
    private String toLogin;
    private String toCurrency;
    private long value;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("fromLogin", fromLogin)
                .append("fromCurrency", fromCurrency)
                .append("toLogin", toLogin)
                .append("toCurrency", toCurrency)
                .append("value", value)
                .toString();
    }
}
