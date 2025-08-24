package ru.yandex.practicum.tarasov.transfer.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.tarasov.transfer.DTO.ResponseDto;
import ru.yandex.practicum.tarasov.transfer.DTO.TransferRequestDto;
import ru.yandex.practicum.tarasov.transfer.service.TransferService;

@RestController
public class TransferController {
    private final TransferService transferService;
    private final MeterRegistry meterRegistry;

    public TransferController(TransferService transferService,
                              MeterRegistry meterRegistry) {
        this.transferService = transferService;
        this.meterRegistry = meterRegistry;
    }

    @PostMapping("/transfer")
    public ResponseDto transfer(@RequestBody TransferRequestDto transferRequestDto) {
        ResponseDto responseDto = transferService.transfer(transferRequestDto);
        if(responseDto.hasErrors()) {
            Counter.builder("transfer.failed")
                    .description("transfer failed")
                    .tag("fromLogin", transferRequestDto.getFromLogin())
                    .tag("toLogin", transferRequestDto.getToLogin())
                    .tag("fromAccount", transferRequestDto.getFromCurrency())
                    .tag("toAccount", transferRequestDto.getToCurrency())
                    .register(this.meterRegistry)
                    .increment();
        }
        return responseDto;
    }
}
