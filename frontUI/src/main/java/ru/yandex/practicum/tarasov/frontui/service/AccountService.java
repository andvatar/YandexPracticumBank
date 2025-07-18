package ru.yandex.practicum.tarasov.frontui.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.tarasov.frontui.DTO.CashDto;
import ru.yandex.practicum.tarasov.frontui.DTO.ResponseDto;
import ru.yandex.practicum.tarasov.frontui.client.cash.CashClient;

@Service
public class AccountService {

    private final CashClient cashClient;

    public AccountService(CashClient cashClient) {
        this.cashClient = cashClient;
    }

    public ResponseDto getPutCash(CashDto cashDto) {
        return cashClient.getPutCash(cashDto);
    }
}
