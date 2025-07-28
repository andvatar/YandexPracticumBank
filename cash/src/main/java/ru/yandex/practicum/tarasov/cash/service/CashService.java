package ru.yandex.practicum.tarasov.cash.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.tarasov.cash.client.accounts.AccountsClient;
import ru.yandex.practicum.tarasov.cash.client.accounts.dto.ResponseDto;
import ru.yandex.practicum.tarasov.cash.client.blocker.BlockerClient;
import ru.yandex.practicum.tarasov.cash.client.blocker.dto.BlockerResponseDto;
import ru.yandex.practicum.tarasov.cash.client.notifications.NotificationsClient;
import ru.yandex.practicum.tarasov.cash.client.notifications.dto.NotificationDto;
import ru.yandex.practicum.tarasov.cash.dto.CashDto;

@Service
public class CashService {
    private final AccountsClient accountsClient;
    private final BlockerClient blockerClient;
    private final NotificationsClient notificationsClient;

    public CashService(AccountsClient accountsClient,
                       BlockerClient blockerClient,
                       NotificationsClient notificationsClient) {
        this.accountsClient = accountsClient;
        this.blockerClient = blockerClient;
        this.notificationsClient = notificationsClient;
    }

    public ResponseDto getPutCash(CashDto cashDto) {

        ResponseDto responseDto = new ResponseDto();

        BlockerResponseDto blockerResponseDto = blockerClient.checkTransaction(cashDto.getAction(), cashDto.getValue());

        if(!blockerResponseDto.isAllowed()) {
            responseDto.errors().add("The operation was blocked: " + blockerResponseDto.reason() + " " + blockerResponseDto.errorMessage());
            return responseDto;
        }

        responseDto = accountsClient.getPutCash(cashDto);

        if(!responseDto.hasErrors()) {
            notificationsClient.sendNotification(new NotificationDto("cash " + cashDto.getAction(), cashDto.getValue() ));
        }

        return responseDto;
    }
}
