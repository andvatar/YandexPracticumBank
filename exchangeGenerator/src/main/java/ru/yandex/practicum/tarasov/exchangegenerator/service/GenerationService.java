package ru.yandex.practicum.tarasov.exchangegenerator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.tarasov.exchangegenerator.client.ExchangeClient;
import ru.yandex.practicum.tarasov.exchangegenerator.client.dto.ExchangeRate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenerationService {
    private final ExchangeClient  exchangeClient;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${kafka.topic}")
    private String kafkaTopic;
    private final Logger log = LoggerFactory.getLogger(GenerationService.class);

    public GenerationService(ExchangeClient exchangeClient,
                             KafkaTemplate<String, Object> kafkaTemplate) {
        this.exchangeClient = exchangeClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 60000)
    public void sendRates() {

        log.info("Sending rates");
        List<ExchangeRate> rates = new ArrayList<>();
        rates.add(new ExchangeRate("RUR", "Russian Ruble", 1.0));
        rates.add(new ExchangeRate("USD", "US Dollar", Math.round(Math.random() * 10000)/100.0));
        rates.add(new ExchangeRate("CNY", "Chinese yuan", Math.round(Math.random() * 10000)/100.0));

        //ResponseDto responseDto = exchangeClient.addRates(rates);

        kafkaTemplate.send(
                    kafkaTopic,
                    rates.stream().map(ExchangeRate::getCurrencyCode).collect(Collectors.joining()),
                    rates
                );

    }
}
