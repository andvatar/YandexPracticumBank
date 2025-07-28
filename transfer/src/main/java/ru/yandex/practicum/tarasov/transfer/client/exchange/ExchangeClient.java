package ru.yandex.practicum.tarasov.transfer.client.exchange;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.tarasov.transfer.configuration.FeignConfig;

@FeignClient(name = "exchange", configuration = FeignConfig.class)
public interface ExchangeClient {
    @GetMapping("/exchange/{fromCurrency}/{toCurrency}/{amount}")
    long exchangeCurrencies(@PathVariable("fromCurrency") String fromCurrency,
                            @PathVariable("toCurrency") String toCurrency,
                            @PathVariable("amount") long amount);
}
