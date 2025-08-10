package ru.yandex.practicum.tarasov.exchangegenerator.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.tarasov.exchangegenerator.DTO.ResponseDto;
import ru.yandex.practicum.tarasov.exchangegenerator.client.dto.ExchangeRate;
import ru.yandex.practicum.tarasov.exchangegenerator.configuration.OAuthFeignConfig;

import java.util.List;

@FeignClient(name = "bank-exchange-service", configuration = OAuthFeignConfig.class)
public interface ExchangeClient {
    @PostMapping("/exchangeRate")
    ResponseDto addRates(@RequestBody List<ExchangeRate> exchangeRates);
}
