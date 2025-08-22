package ru.yandex.practicum.tarasov.cash.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.tarasov.cash.client.accounts.AccountsClient;
import ru.yandex.practicum.tarasov.cash.client.accounts.dto.ResponseDto;
import ru.yandex.practicum.tarasov.cash.client.blocker.BlockerClient;
import ru.yandex.practicum.tarasov.cash.client.blocker.dto.BlockerRequestDto;
import ru.yandex.practicum.tarasov.cash.client.blocker.dto.BlockerResponseDto;
import ru.yandex.practicum.tarasov.cash.client.notifications.NotificationsClient;
import ru.yandex.practicum.tarasov.cash.client.notifications.dto.NotificationDto;
import ru.yandex.practicum.tarasov.cash.dto.CashDto;

@Service
@Slf4j
public class CashService {
    private final AccountsClient accountsClient;
    private final BlockerClient blockerClient;
    private final NotificationsClient notificationsClient;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${kafka.topic}")
    private String kafkaTopic;

    public CashService(AccountsClient accountsClient,
                       BlockerClient blockerClient,
                       NotificationsClient notificationsClient,
                       KafkaTemplate<String, Object> kafkaTemplate) {
        this.accountsClient = accountsClient;
        this.blockerClient = blockerClient;
        this.notificationsClient = notificationsClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    public ResponseDto getPutCash(CashDto cashDto) {

        log.info("getPutCash cashDto={}", cashDto);

        ResponseDto responseDto = new ResponseDto();

        BlockerResponseDto blockerResponseDto = blockerClient.checkTransaction(
                new BlockerRequestDto(cashDto.getUsername(),
                        "",
                        cashDto.getCurrency(),
                        "",
                        "cash",
                        cashDto.getValue()
                ));

        if(!blockerResponseDto.isAllowed()) {
            responseDto.errors().add("The operation was blocked: " + blockerResponseDto.reason() + " " + blockerResponseDto.errorMessage());
            log.warn("The transaction was blocked: {} {}", blockerResponseDto.reason(), blockerResponseDto.errorMessage());
            return responseDto;
        }

        responseDto = accountsClient.getPutCash(cashDto);

        if(!responseDto.hasErrors()) {
            //notificationsClient.sendNotification(new NotificationDto("cash " + cashDto.getAction(), cashDto.getValue() ));
            kafkaTemplate.send(
                    kafkaTopic,
                    new NotificationDto("cash " + cashDto.getAction(), cashDto.getValue() )
            );
        }

        return responseDto;
    }
}
