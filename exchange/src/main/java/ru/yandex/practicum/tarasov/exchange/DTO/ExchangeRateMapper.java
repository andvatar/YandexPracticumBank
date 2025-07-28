package ru.yandex.practicum.tarasov.exchange.DTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.yandex.practicum.tarasov.exchange.entity.ExchangeRate;

@Mapper(componentModel = "spring")
public interface ExchangeRateMapper {
    @Mapping(source = "currencyCode", target = "name")
    @Mapping(source = "currencyName", target = "title")
    @Mapping(source = "rate", target = "value")
    ExchangeRateDto toExchangeRateDto(ExchangeRate exchangeRate);}
