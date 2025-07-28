package ru.yandex.practicum.tarasov.cash.client.notifications;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.tarasov.cash.client.accounts.dto.ResponseDto;
import ru.yandex.practicum.tarasov.cash.client.notifications.dto.NotificationDto;
import ru.yandex.practicum.tarasov.cash.configuration.FeignConfig;


@FeignClient(name = "notifications", configuration = FeignConfig.class)
public interface NotificationsClient {
    @PostMapping("/notify")
    ResponseDto sendNotification(@RequestBody NotificationDto notificationDto);
}
