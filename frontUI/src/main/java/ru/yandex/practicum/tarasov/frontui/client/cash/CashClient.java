package ru.yandex.practicum.tarasov.frontui.client.cash;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import ru.yandex.practicum.tarasov.frontui.DTO.CashDto;
import ru.yandex.practicum.tarasov.frontui.DTO.ResponseDto;
import ru.yandex.practicum.tarasov.frontui.configuration.OAuthFeignConfig;

@FeignClient(name = "cash", configuration = OAuthFeignConfig.class)
public interface CashClient {

    @PostMapping("account/cash")
    ResponseDto getPutCash(CashDto cashDto);
}
