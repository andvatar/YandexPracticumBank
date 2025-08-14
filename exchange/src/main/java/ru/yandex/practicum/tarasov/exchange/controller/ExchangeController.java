package ru.yandex.practicum.tarasov.exchange.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.tarasov.exchange.DTO.ExchangeRateDto;
import ru.yandex.practicum.tarasov.exchange.entity.ExchangeRate;
import ru.yandex.practicum.tarasov.exchange.service.ExchangeService;

import java.util.List;

@RestController
public class ExchangeController {
    private final ExchangeService exchangeService;
    private static final Logger log = LoggerFactory.getLogger(ExchangeController.class);


    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    /*@PostMapping("/exchangeRate")
    public ResponseDto addExchangeRate(@RequestBody List<ExchangeRate> exchangeRates) {
        return exchangeService.addRates(exchangeRates);
    }*/

    @GetMapping("/exchange/{fromCurrency}/{toCurrency}/{amount}")
    public long exchangeCurrencies(@PathVariable("amount") long amount,
                                   @PathVariable("fromCurrency") String fromCurrency,
                                   @PathVariable("toCurrency") String toCurrency) {
        return exchangeService.convert(amount, fromCurrency, toCurrency);
    }

    @GetMapping("/rates")
    //@CrossOrigin(origins = {"http://front-ui.test.local", "http://front-ui.prod.local"})
    public List<ExchangeRateDto> getExchangeRates() {
        return exchangeService.getExchangeRates();
    }

    @KafkaListener(topics = "${kafka.topic}")
    public void addExchangeRates(List<ExchangeRate> exchangeRates) {
        log.info("Received rates: {}", exchangeRates);
        var responseDto = exchangeService.addRates(exchangeRates);
        log.info("Added rates: {}", responseDto);
    }
}
