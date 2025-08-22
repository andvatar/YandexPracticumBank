package ru.yandex.practicum.tarasov.exchange.service;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.tarasov.exchange.DTO.ExchangeRateDto;
import ru.yandex.practicum.tarasov.exchange.DTO.ExchangeRateMapper;
import ru.yandex.practicum.tarasov.exchange.entity.ExchangeRate;
import ru.yandex.practicum.tarasov.exchange.repository.ExchangeRepository;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Slf4j
public class ExchangeService {
    private final ExchangeRepository  exchangeRepository;
    private final ExchangeRateMapper exchangeRateMapper;
    private final AtomicLong lastSuccessfulAddRates;
    //private final Logger log = LoggerFactory.getLogger(ExchangeService.class);

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
        log.info("Adding rates");
        try {
            exchangeRepository.saveAll(rates);
            lastSuccessfulAddRates.set(System.currentTimeMillis());
        } catch (Exception ex) {
            log.error("Error adding rates", ex);
        }
    }

    public long convert(long amount, String fromCurrency, String toCurrency) {

        log.info("Converting {} from {} to {}", amount, fromCurrency,  toCurrency);
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

        log.info("New amount: {}", amount);

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
