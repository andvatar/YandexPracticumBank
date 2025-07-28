package ru.yandex.practicum.tarasov.frontui.client.transfer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import ru.yandex.practicum.tarasov.frontui.DTO.ResponseDto;
import ru.yandex.practicum.tarasov.frontui.client.transfer.dto.TransferRequestDto;
import ru.yandex.practicum.tarasov.frontui.configuration.OAuthFeignConfig;

@FeignClient(name = "transfer", configuration = OAuthFeignConfig.class)
public interface TransferClient {
    @PostMapping("/transfer")
    ResponseDto transfer(TransferRequestDto transferRequestDto);
}
