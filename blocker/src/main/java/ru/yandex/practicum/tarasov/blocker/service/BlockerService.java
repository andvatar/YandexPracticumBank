package ru.yandex.practicum.tarasov.blocker.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.tarasov.blocker.DTO.BlockerRequestDto;
import ru.yandex.practicum.tarasov.blocker.DTO.BlockerResponseDto;

@Service
@Slf4j
public class BlockerService {
    @Value("${suspect-amount}")
    private long suspectAmount;

    private final MeterRegistry registry;

    public BlockerService(MeterRegistry registry) {
        this.registry = registry;
    }

    public BlockerResponseDto checkTransaction(BlockerRequestDto blockerRequestDto) {

        log.info("checkTransaction started with {}", blockerRequestDto);

        boolean isAllowed = blockerRequestDto.amount() % suspectAmount != 0;
        String reason = "";

        if(!isAllowed) {
            reason = "Transactions divisible by " + suspectAmount + " are very suspicious";
            Counter.builder("transaction.blocker")
                    .description("Number of transactions blocked")
                    .tag("fromUser", blockerRequestDto.fromUser())
                    .tag("toUser", blockerRequestDto.toUser())
                    .tag("fromAccount", blockerRequestDto.fromAccount())
                    .tag("toAccount", blockerRequestDto.toAccount())
                    .tag("operation", blockerRequestDto.action())
                    .register(registry)
                    .increment();

        }

        return new BlockerResponseDto(isAllowed, reason, "");
    }
}
