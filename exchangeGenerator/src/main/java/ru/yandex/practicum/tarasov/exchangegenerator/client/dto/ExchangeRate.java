package ru.yandex.practicum.tarasov.exchangegenerator.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExchangeRate {
    private String currencyCode;
    private String currencyName;
    private double rate;
}
