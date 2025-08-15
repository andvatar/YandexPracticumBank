package ru.yandex.practicum.tarasov.notifications.configuration;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.yandex.practicum.tarasov.notifications.DTO.NotificationDto;

import java.util.Map;

@Configuration
public class KafkaConfig {
    @Bean
    public ConsumerFactory<String, NotificationDto> consumerFactory(KafkaProperties properties) {
        Map<String, Object> configProps = properties.buildConsumerProperties();

        return new DefaultKafkaConsumerFactory<>(
                configProps,
                new StringDeserializer(),
                new JsonDeserializer<>(NotificationDto.class, false)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, NotificationDto> kafkaListenerContainerFactory(KafkaProperties kafkaProperties) {
        ConcurrentKafkaListenerContainerFactory<String, NotificationDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(kafkaProperties));
        factory.setBatchListener(false);
        return factory;
    }
}
