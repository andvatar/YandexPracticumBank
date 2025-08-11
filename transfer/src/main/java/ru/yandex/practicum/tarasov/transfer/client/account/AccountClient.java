package ru.yandex.practicum.tarasov.transfer.client.account;

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.tarasov.transfer.DTO.ResponseDto;
import ru.yandex.practicum.tarasov.transfer.DTO.TransferDto;
import ru.yandex.practicum.tarasov.transfer.configuration.FeignConfig;

import java.util.ArrayList;
import java.util.List;


@FeignClient(name = "bank-accounts-service", configuration = FeignConfig.class)
public interface AccountClient {
    @PostMapping("/account/transfer")
    @Retry(name = "transfer", fallbackMethod = "transferFallback")
    ResponseDto transfer(@RequestBody TransferDto transferDto);

    default ResponseDto transferFallback(@PathVariable String operation, @PathVariable long amount, Throwable cause) {
        List<String> error = new ArrayList<>();
        error.add(cause.getMessage());
        return new ResponseDto(error);
    }
}
