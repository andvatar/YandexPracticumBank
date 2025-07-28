package ru.yandex.practicum.tarasov.exchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.tarasov.exchange.entity.ExchangeRate;

@Repository
public interface ExchangeRepository extends JpaRepository<ExchangeRate, String> {
    ExchangeRate findByCurrencyCode(String currencyCode);
}
