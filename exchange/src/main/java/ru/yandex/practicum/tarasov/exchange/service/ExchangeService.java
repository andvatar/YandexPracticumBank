package ru.yandex.practicum.tarasov.exchange.service;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.tarasov.exchange.DTO.ExchangeRateDto;
import ru.yandex.practicum.tarasov.exchange.DTO.ExchangeRateMapper;
import ru.yandex.practicum.tarasov.exchange.entity.ExchangeRate;
import ru.yandex.practicum.tarasov.exchange.repository.ExchangeRepository;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ExchangeService {
    private final ExchangeRepository  exchangeRepository;
    private final ExchangeRateMapper exchangeRateMapper;
    private final AtomicLong lastSuccessfulAddRates;

    public ExchangeService(ExchangeRepository exchangeRepository,
                           ExchangeRateMapper exchangeRateMapper,
                           MeterRegistry meterRegistry) {
        this.exchangeRepository = exchangeRepository;
        this.exchangeRateMapper = exchangeRateMapper;
        this.lastSuccessfulAddRates = new AtomicLong(System.currentTimeMillis());
        Gauge.builder("exchange.success.import.time",
                        lastSuccessfulAddRates,
                        value -> (System.currentTimeMillis() - value.get()) / 1000.0)
                .description("Time elapsed in seconds since the last successful rates import")
                .register(meterRegistry);
    }

    public void addRates(List<ExchangeRate> rates) {
        exchangeRepository.saveAll(rates);
        lastSuccessfulAddRates.set(System.currentTimeMillis());
    }

    public long convert(long amount, String fromCurrency, String toCurrency) {


        if(!fromCurrency.equals("RUR") && !toCurrency.equals("RUR")) {
            amount = convert(amount, fromCurrency, "RUR");
            fromCurrency = "RUR";
        }


        if(fromCurrency.equals("RUR")) {
            amount = Math.round(amount / getRate(toCurrency));
        }
        else {
            amount = Math.round(amount * getRate(fromCurrency));
        }

        return amount;
    }

    public List<ExchangeRateDto> getExchangeRates() {
        List<ExchangeRate>  exchangeRates = exchangeRepository.findAll();
        return exchangeRates.stream().map(exchangeRateMapper::toExchangeRateDto).toList();
    }

    private double getRate(String toCurrency) {
        return exchangeRepository.findByCurrencyCode(toCurrency).getRate();
    }
}
