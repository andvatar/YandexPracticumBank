package ru.yandex.practicum.tarasov.cash.client.accounts;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.tarasov.cash.client.accounts.dto.ResponseDto;
import ru.yandex.practicum.tarasov.cash.configuration.CashFeignConfig;
import ru.yandex.practicum.tarasov.cash.dto.CashDto;

@FeignClient(name = "accounts", configuration = CashFeignConfig.class)
public interface AccountsClient {
    @PostMapping("/account/cash")
    ResponseDto getPutCash(@RequestBody CashDto cashDto);

}
