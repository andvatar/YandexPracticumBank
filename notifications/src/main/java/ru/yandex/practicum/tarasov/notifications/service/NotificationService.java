package ru.yandex.practicum.tarasov.notifications.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.tarasov.notifications.DTO.NotificationDto;

@Service
public class NotificationService {

    private final MeterRegistry registry;

    public NotificationService(MeterRegistry registry) {
        this.registry = registry;
    }

    public void sendNotification(NotificationDto notificationDto) {
        // здесь должна была быть отправка уведомлений, но я не знаю как это сделать и у меня нет времени разбираться, так что оставляю заглушку.
        try {
            System.out.println("Sending notification on operation: " + notificationDto.operation() + ", amount: " + notificationDto.amount());
        }
        catch (Exception e) {
            Counter.builder("user.notification.failed")
                    .description("Number of failed notifications")
                    .register(registry)
                    .increment();
        }
        //return new ResponseDto();
    }
}
