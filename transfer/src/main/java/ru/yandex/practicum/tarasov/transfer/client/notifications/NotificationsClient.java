package ru.yandex.practicum.tarasov.transfer.client.notifications;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.tarasov.transfer.DTO.ResponseDto;
import ru.yandex.practicum.tarasov.transfer.client.notifications.dto.NotificationDto;
import ru.yandex.practicum.tarasov.transfer.configuration.FeignConfig;

@FeignClient(name = "bank-notifications-service", configuration = FeignConfig.class)
public interface NotificationsClient {
    @PostMapping("/notify")
    ResponseDto sendNotification(@RequestBody NotificationDto notificationDto);
}
