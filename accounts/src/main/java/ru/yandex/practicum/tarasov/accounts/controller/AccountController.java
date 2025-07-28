package ru.yandex.practicum.tarasov.accounts.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.tarasov.accounts.DTO.CashDto;
import ru.yandex.practicum.tarasov.accounts.DTO.ResponseDto;
import ru.yandex.practicum.tarasov.accounts.DTO.TransferDto;
import ru.yandex.practicum.tarasov.accounts.service.AccountService;

@RestController
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/account/cash")
    public ResponseDto getPutCash(@RequestBody CashDto cashDto) {
        return accountService.getPutCash(cashDto);
    }

    @PostMapping("/account/transfer")
    public ResponseDto transfer(@RequestBody TransferDto transferDto) {
        return accountService.transfer(transferDto);
    }
}
