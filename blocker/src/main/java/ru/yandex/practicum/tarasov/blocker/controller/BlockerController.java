package ru.yandex.practicum.tarasov.blocker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.tarasov.blocker.DTO.BlockerResponseDto;
import ru.yandex.practicum.tarasov.blocker.service.BlockerService;

@RestController
public class BlockerController {
    private final BlockerService blockerService;

    public BlockerController(BlockerService blockerService) {
        this.blockerService = blockerService;
    }

    @GetMapping("/check/{operation}/{amount}")
    public BlockerResponseDto checkTransaction(@PathVariable String operation, @PathVariable long amount) {
        return blockerService.checkTransaction(operation, amount);
    }
}
