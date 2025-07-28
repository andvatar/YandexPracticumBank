package ru.yandex.practicum.tarasov.accounts.DTO;

public record TransferDto(String fromLogin,
                          String fromCurrency,
                          long fromAmount,
                          String toLogin,
                          String toCurrency,
                          long toAmount) {
}
