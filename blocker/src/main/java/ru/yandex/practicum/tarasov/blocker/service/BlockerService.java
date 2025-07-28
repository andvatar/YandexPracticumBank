package ru.yandex.practicum.tarasov.blocker.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.tarasov.blocker.DTO.BlockerResponseDto;

@Service
public class BlockerService {
    @Value("${suspect-amount}")
    private long suspectAmount;

    public BlockerResponseDto checkTransaction(String operation, long amount) {

        boolean isAllowed = amount % suspectAmount != 0;
        String reason = "";

        if(!isAllowed) {
            reason = "Transactions divisible by " + suspectAmount + " are very suspicious";
        }

        return new BlockerResponseDto(isAllowed, reason, "");
    }
}
