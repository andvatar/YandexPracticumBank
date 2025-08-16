package ru.yandex.practicum.tarasov.exchangegenerator.integration;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.yandex.practicum.tarasov.exchangegenerator.ExchangeGeneratorApplication;
import ru.yandex.practicum.tarasov.exchangegenerator.client.dto.ExchangeRate;
import ru.yandex.practicum.tarasov.exchangegenerator.service.GenerationService;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ExchangeGeneratorApplication.class)
@EmbeddedKafka(topics = {"exchange-rates-topic"}, partitions = 1,
        brokerProperties = {
                "listeners=PLAINTEXT://localhost:9092",
                "port=9092"
        })
@ActiveProfiles("IntegrationTest")
@TestPropertySource(properties = "kafka.topic=exchange-rates-topic")
@DirtiesContext
public class RatesGenerationTest {

    @Autowired
    private GenerationService generationService;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Test
    void generateRatesTest() {

        ObjectMapper om = new ObjectMapper();
        JavaType type = om.getTypeFactory().constructParametricType(List.class, ExchangeRate.class);
        JsonDeserializer<List<ExchangeRate>> deserializer = new JsonDeserializer<>(type, om, false);

        try (var consumerForTest = new DefaultKafkaConsumerFactory<>(
                KafkaTestUtils.consumerProps("exchange-rates", "true", embeddedKafkaBroker),
                new StringDeserializer(),
                deserializer
        ).createConsumer()) {
            consumerForTest.subscribe(List.of("exchange-rates-topic"));
            generationService.sendRates();
            var inputMessage = KafkaTestUtils.getSingleRecord(consumerForTest, "exchange-rates-topic", Duration.ofSeconds(5));
            assertThat(inputMessage.key()).isEqualTo("RURUSDCNY");
            assertThat(inputMessage.value().size()).isEqualTo(3);
            assertThat(inputMessage.value().stream().map(ExchangeRate::getCurrencyCode).collect(Collectors.joining())).isEqualTo("RURUSDCNY");
        }
    }
}
