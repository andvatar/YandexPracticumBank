package ru.yandex.practicum.tarasov.blocker.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.tarasov.blocker.DTO.BlockerRequestDto;
import ru.yandex.practicum.tarasov.blocker.DTO.BlockerResponseDto;
import ru.yandex.practicum.tarasov.blocker.service.BlockerService;

@RestController
public class BlockerController {
    private final BlockerService blockerService;

    public BlockerController(BlockerService blockerService) {
        this.blockerService = blockerService;
    }

    /*@GetMapping("/check/{operation}/{amount}")
    public BlockerResponseDto checkTransaction(@PathVariable String operation, @PathVariable long amount) {
        return blockerService.checkTransaction(operation, amount);
    }*/

    @PostMapping("/check")
    public BlockerResponseDto checkTransaction(@RequestBody BlockerRequestDto blockerRequestDto) {
        return blockerService.checkTransaction(blockerRequestDto);
    }
}
