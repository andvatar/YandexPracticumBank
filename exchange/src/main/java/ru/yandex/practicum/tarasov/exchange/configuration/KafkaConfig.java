package ru.yandex.practicum.tarasov.exchange.configuration;

import com.fasterxml.jackson.core.type.TypeReference;
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

        return new DefaultKafkaConsumerFactory<>(
                configProps,
                new StringDeserializer(),
                new JsonDeserializer<List<ExchangeRate>>() {
                    @Override
                    public List<ExchangeRate> deserialize(String topic, byte[] data) {
                        try {
                            ObjectMapper mapper = new ObjectMapper();
                            return mapper.readValue(data,
                                    mapper.getTypeFactory().constructCollectionType(List.class, ExchangeRate.class));
                        } catch (IOException e) {
                            throw new SerializationException("Error deserializing message", e);
                        }
                    }
                }
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, List<ExchangeRate>> kafkaListenerContainerFactory(KafkaProperties kafkaProperties) {
        ConcurrentKafkaListenerContainerFactory<String, List<ExchangeRate>> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(kafkaProperties));
        return factory;
    }
}
