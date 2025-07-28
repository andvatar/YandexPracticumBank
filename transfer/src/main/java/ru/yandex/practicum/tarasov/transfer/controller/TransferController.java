package ru.yandex.practicum.tarasov.transfer.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.tarasov.transfer.DTO.ResponseDto;
import ru.yandex.practicum.tarasov.transfer.DTO.TransferRequestDto;
import ru.yandex.practicum.tarasov.transfer.service.TransferService;

@RestController
public class TransferController {
    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/transfer")
    public ResponseDto transfer(@RequestBody TransferRequestDto transferRequestDto) {
        return transferService.transfer(transferRequestDto);
    }
}
