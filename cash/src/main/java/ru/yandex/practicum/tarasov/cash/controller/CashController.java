package ru.yandex.practicum.tarasov.cash.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.tarasov.cash.client.accounts.dto.ResponseDto;
import ru.yandex.practicum.tarasov.cash.dto.CashDto;
import ru.yandex.practicum.tarasov.cash.service.CashService;

@RestController
public class CashController {
    private final CashService cashService;

    public CashController(CashService cashService) {
        this.cashService = cashService;
    }

    @PostMapping("account/cash")
    public ResponseDto getPutCash(@RequestBody CashDto cashDto) {
        return cashService.getPutCash(cashDto);
    }
}
