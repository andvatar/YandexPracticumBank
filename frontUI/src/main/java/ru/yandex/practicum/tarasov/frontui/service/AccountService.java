package ru.yandex.practicum.tarasov.frontui.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.tarasov.frontui.DTO.CashDto;
import ru.yandex.practicum.tarasov.frontui.DTO.ResponseDto;
import ru.yandex.practicum.tarasov.frontui.client.cash.CashClient;
import ru.yandex.practicum.tarasov.frontui.client.transfer.TransferClient;
import ru.yandex.practicum.tarasov.frontui.client.transfer.dto.TransferRequestDto;

@Service
public class AccountService {

    private final CashClient cashClient;
    private final TransferClient transferClient;
    private final Logger log = LoggerFactory.getLogger(AccountService.class);

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
