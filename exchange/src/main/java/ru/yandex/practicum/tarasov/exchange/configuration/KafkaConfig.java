package ru.yandex.practicum.tarasov.exchange.configuration;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.yandex.practicum.tarasov.exchange.entity.ExchangeRate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Configuration
public class KafkaConfig {
    @Bean
    public ConsumerFactory<String, List<ExchangeRate>> consumerFactory(KafkaProperties properties) {
        Map<String, Object> configProps = properties.buildConsumerProperties();

        //JsonDeserializer<ExchangeRate> valueDeserializer = new JsonDeserializer<>(ExchangeRate.class);
        //valueDeserializer.addTrustedPackages("ru.yandex.practicum.tarasov.exchange.entity");

        JsonDeserializer<List<ExchangeRate>> deserializer = new JsonDeserializer<>() {
            private final ObjectMapper objectMapper = new ObjectMapper();

            @Override
            public List<ExchangeRate> deserialize(String topic, byte[] data) {
                try {
                    JavaType type = objectMapper.getTypeFactory()
                            .constructCollectionType(List.class, ExchangeRate.class);
                    return objectMapper.readValue(data, type);
                } catch (IOException e) {
                    throw new SerializationException("Error deserializing message", e);
                }
            }
        };

        deserializer.addTrustedPackages("ru.yandex.practicum.tarasov.exchange.entity");

        return new DefaultKafkaConsumerFactory<>(
                configProps,
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, List<ExchangeRate>> kafkaListenerContainerFactory(KafkaProperties kafkaProperties) {
        ConcurrentKafkaListenerContainerFactory<String, List<ExchangeRate>> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(kafkaProperties));
        factory.setBatchListener(true);
        return factory;
    }
}
