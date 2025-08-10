package ru.yandex.practicum.tarasov.cash.client.blocker;

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.tarasov.cash.client.blocker.dto.BlockerResponseDto;
import ru.yandex.practicum.tarasov.cash.configuration.FeignConfig;

@FeignClient(name = "bank-blocker-service", configuration = FeignConfig.class)
public interface BlockerClient {

    @GetMapping("/check/{operation}/{amount}")
    @Retry(name = "checkTransaction", fallbackMethod = "checkTransactionFallback")
    BlockerResponseDto checkTransaction(@PathVariable String operation, @PathVariable long amount);

    default BlockerResponseDto checkTransactionFallback(@PathVariable String operation, @PathVariable long amount, Throwable cause) {
        return new BlockerResponseDto(false,
                "Error during validation",
                cause.getMessage());
    }
}
