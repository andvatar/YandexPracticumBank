package ru.yandex.practicum.tarasov.cash.client.blocker;

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.tarasov.cash.client.blocker.dto.BlockerRequestDto;
import ru.yandex.practicum.tarasov.cash.client.blocker.dto.BlockerResponseDto;
import ru.yandex.practicum.tarasov.cash.configuration.FeignConfig;

@FeignClient(name = "bank-blocker-service", configuration = FeignConfig.class)
public interface BlockerClient {

    @PostMapping("/check")
    @Retry(name = "checkTransaction", fallbackMethod = "checkTransactionFallback")
    BlockerResponseDto checkTransaction(@RequestBody BlockerRequestDto blockerRequestDto);

    default BlockerResponseDto checkTransactionFallback(@RequestBody BlockerRequestDto blockerRequestDto, Throwable cause) {
        return new BlockerResponseDto(false,
                "Error during validation",
                cause.getMessage());
    }
}
