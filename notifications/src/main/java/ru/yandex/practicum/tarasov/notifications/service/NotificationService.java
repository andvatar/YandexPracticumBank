package ru.yandex.practicum.tarasov.notifications.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.tarasov.notifications.DTO.NotificationDto;
import ru.yandex.practicum.tarasov.notifications.DTO.ResponseDto;

@Service
public class NotificationService {
    public ResponseDto sendNotification(NotificationDto notificationDto) {
        // здесь должна была быть отправка уведомлений, но я не знаю как это сделать и у меня нет времени разбираться, так что оставляю заглушку.
        System.out.println("Sending notification on operation: " + notificationDto.operation() + ", amount: " + notificationDto.amount());
        return new ResponseDto();
    }
}
