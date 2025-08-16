package ru.yandex.practicum.tarasov.notifications.controller;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.tarasov.notifications.DTO.NotificationDto;
import ru.yandex.practicum.tarasov.notifications.service.NotificationService;

@RestController
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /*@PostMapping("/notify")
    public ResponseDto sendNotification(@RequestBody NotificationDto notificationDto) {
        return notificationService.sendNotification(notificationDto);
    }*/

    @KafkaListener(topics = "${kafka.topic}")
    public void sendNotification(@Payload NotificationDto notificationDto) {
        notificationService.sendNotification(notificationDto);
    }
}
