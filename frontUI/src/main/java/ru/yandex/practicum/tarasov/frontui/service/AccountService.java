package ru.yandex.practicum.tarasov.frontui.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.tarasov.frontui.DTO.CashDto;
import ru.yandex.practicum.tarasov.frontui.DTO.ResponseDto;
import ru.yandex.practicum.tarasov.frontui.client.cash.CashClient;
import ru.yandex.practicum.tarasov.frontui.client.transfer.TransferClient;
import ru.yandex.practicum.tarasov.frontui.client.transfer.dto.TransferRequestDto;

@Service
@Slf4j
public class AccountService {

    private final CashClient cashClient;
    private final TransferClient transferClient;

    public AccountService(CashClient cashClient,
                          TransferClient transferClient) {
        this.cashClient = cashClient;
        this.transferClient = transferClient;
    }

    public ResponseDto getPutCash(CashDto cashDto) {
        log.info("getPutCash cashDto={}", cashDto);
        return cashClient.getPutCash(cashDto);
    }

    public ResponseDto transfer(TransferRequestDto transferRequestDto) {
        log.info("transfer transferRequestDto={}", transferRequestDto);
        return transferClient.transfer(transferRequestDto);
    }
}
