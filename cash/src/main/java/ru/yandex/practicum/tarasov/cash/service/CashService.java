package ru.yandex.practicum.tarasov.cash.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.tarasov.cash.client.accounts.AccountsClient;
import ru.yandex.practicum.tarasov.cash.client.accounts.dto.ResponseDto;
import ru.yandex.practicum.tarasov.cash.dto.CashDto;

@Service
public class CashService {
    private final AccountsClient accountsClient;

    public CashService(AccountsClient accountsClient) {
        this.accountsClient = accountsClient;
    }

    public ResponseDto getPutCash(CashDto cashDto) {
        System.out.println(cashDto);
        return accountsClient.getPutCash(cashDto);
    }
}
