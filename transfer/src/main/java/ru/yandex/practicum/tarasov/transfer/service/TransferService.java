package ru.yandex.practicum.tarasov.transfer.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.tarasov.transfer.DTO.ResponseDto;
import ru.yandex.practicum.tarasov.transfer.DTO.TransferDto;
import ru.yandex.practicum.tarasov.transfer.DTO.TransferRequestDto;
import ru.yandex.practicum.tarasov.transfer.client.account.AccountClient;
import ru.yandex.practicum.tarasov.transfer.client.blocker.BlockerClient;
import ru.yandex.practicum.tarasov.transfer.client.blocker.dto.BlockerResponseDto;
import ru.yandex.practicum.tarasov.transfer.client.exchange.ExchangeClient;
import ru.yandex.practicum.tarasov.transfer.client.notifications.NotificationsClient;
import ru.yandex.practicum.tarasov.transfer.client.notifications.dto.NotificationDto;

@Service
public class TransferService {
    private final ExchangeClient exchangeClient;
    private final AccountClient accountClient;
    private final BlockerClient blockerClient;
    private final NotificationsClient notificationsClient;

    public TransferService(ExchangeClient exchangeClient,
                           AccountClient accountClient,
                           BlockerClient blockerClient,
                           NotificationsClient notificationsClient) {
        this.exchangeClient = exchangeClient;
        this.accountClient = accountClient;
        this.blockerClient = blockerClient;
        this.notificationsClient = notificationsClient;
    }

    public ResponseDto transfer(TransferRequestDto transferRequestDto) {

        ResponseDto responseDto = new ResponseDto();

        System.out.println(transferRequestDto);

        long fromAmount = exchangeClient.exchangeCurrencies(
                transferRequestDto.getToCurrency(),
                transferRequestDto.getFromCurrency(),
                transferRequestDto.getValue());

        BlockerResponseDto blockerResponseDto = blockerClient.checkTransaction("transfer", fromAmount);

        if(!blockerResponseDto.isAllowed()) {
            responseDto.errors().add("The operation was blocked: " + blockerResponseDto.reason() + " " + blockerResponseDto.errorMessage());
            return responseDto;
        }

        TransferDto transferDto = new TransferDto(transferRequestDto.getFromLogin(),
                transferRequestDto.getFromCurrency(),
                fromAmount,
                transferRequestDto.getToLogin(),
                transferRequestDto.getToCurrency(),
                transferRequestDto.getValue());

        responseDto = accountClient.transfer(transferDto);

        if(!responseDto.hasErrors()) {
            notificationsClient.sendNotification(new NotificationDto("transfer", transferRequestDto.getValue()));
        }

        return responseDto;
    }
}
