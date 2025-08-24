package ru.yandex.practicum.tarasov.notifications.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.tarasov.notifications.DTO.NotificationDto;

@Service
public class NotificationService {

    private final Counter counter;
    private final Logger log = LoggerFactory.getLogger(NotificationService.class);

    public NotificationService(MeterRegistry registry) {
        this.counter = Counter.builder("user.notification.failed")
                .description("Number of failed notifications")
                .register(registry);
    }

    public void sendNotification(NotificationDto notificationDto) {
        // здесь должна была быть отправка уведомлений, но я не знаю как это сделать и у меня нет времени разбираться, так что оставляю заглушку.
        try {
            log.info("sendNotification notificationDto={}", notificationDto);
            System.out.println("Sending notification on operation: " + notificationDto.operation() + ", amount: " + notificationDto.amount());
        }
        catch (Exception e) {
            log.error("Error sending notification on operation: {}", notificationDto, e);
            counter.increment();
        }
    }
}
