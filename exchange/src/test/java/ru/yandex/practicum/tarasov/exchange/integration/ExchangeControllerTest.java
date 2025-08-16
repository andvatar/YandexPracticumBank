package ru.yandex.practicum.tarasov.exchange.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.yandex.practicum.tarasov.exchange.ExchangeApplication;
import ru.yandex.practicum.tarasov.exchange.entity.ExchangeRate;
import ru.yandex.practicum.tarasov.exchange.repository.ExchangeRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ExchangeApplication.class)
@EmbeddedKafka(topics = {"exchange-rates-topic"}, partitions = 1)
@ActiveProfiles("IntegrationTest")
@TestPropertySource(properties = "kafka.topic=exchange-rates-topic")
public class ExchangeControllerTest {
    @Autowired
    private KafkaTemplate<String, List<ExchangeRate>> kafkaTemplate;

    @Autowired
    private ExchangeRepository exchangeRepository;

    @Test
    void addExchangeRatesTest() {
        List<ExchangeRate> testRates = List.of(
                new ExchangeRate("RUR", "Russian Ruble", 1),
                new ExchangeRate("USD", "US dollar", 100),
                new ExchangeRate("CNY", "Chinese yuan", 50)
        );

        kafkaTemplate.send("exchange-rates-topic",
                testRates.stream().map(ExchangeRate::getCurrencyCode).collect(Collectors.joining()),
                testRates);

        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() ->
            assertEquals(testRates.size(), exchangeRepository.count())
        );

    }

}
