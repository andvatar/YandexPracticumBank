package ru.yandex.practicum.tarasov.blocker.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.tarasov.blocker.DTO.BlockerRequestDto;
import ru.yandex.practicum.tarasov.blocker.DTO.BlockerResponseDto;

@Service
public class BlockerService {
    @Value("${suspect-amount}")
    private long suspectAmount;

    //private final MeterRegistry registry;
    private final Counter counter;
    private final Logger log = LoggerFactory.getLogger(BlockerService.class);

    public BlockerService(MeterRegistry registry) {
        //this.registry = registry;
        this.counter = Counter.builder("transaction.blocker")
                .description("number of transactions blocked")
                .register(registry);
    }

    public BlockerResponseDto checkTransaction(BlockerRequestDto blockerRequestDto) {

        log.info("checkTransaction started with {}", blockerRequestDto);

        boolean isAllowed = blockerRequestDto.amount() % suspectAmount != 0;
        String reason = "";

        if(!isAllowed) {
            reason = "Transactions divisible by " + suspectAmount + " are very suspicious";
            counter.increment();
            /*Counter.builder("transaction.blocker")
                    .description("Number of transactions blocked")
                    .tag("fromUser", blockerRequestDto.fromUser())
                    .tag("toUser", blockerRequestDto.toUser())
                    .tag("fromAccount", blockerRequestDto.fromAccount())
                    .tag("toAccount", blockerRequestDto.toAccount())
                    .register(registry)
                    .increment();*/

        }

        return new BlockerResponseDto(isAllowed, reason, "");
    }
}
