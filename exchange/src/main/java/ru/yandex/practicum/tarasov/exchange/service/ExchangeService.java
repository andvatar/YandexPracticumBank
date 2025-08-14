package ru.yandex.practicum.tarasov.exchange.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.tarasov.exchange.DTO.ExchangeRateDto;
import ru.yandex.practicum.tarasov.exchange.DTO.ExchangeRateMapper;
import ru.yandex.practicum.tarasov.exchange.DTO.ResponseDto;
import ru.yandex.practicum.tarasov.exchange.entity.ExchangeRate;
import ru.yandex.practicum.tarasov.exchange.repository.ExchangeRepository;

import java.util.List;

@Service
public class ExchangeService {
    private final ExchangeRepository  exchangeRepository;
    private final ExchangeRateMapper exchangeRateMapper;

    public ExchangeService(ExchangeRepository exchangeRepository,
                           ExchangeRateMapper exchangeRateMapper) {
        this.exchangeRepository = exchangeRepository;
        this.exchangeRateMapper = exchangeRateMapper;
    }

    public void addRates(List<ExchangeRate> rates) {
        exchangeRepository.saveAll(rates);
    }
        //ResponseDto responseDto = new ResponseDto();
        //try {

        //} catch (Exception e) {
        //    responseDto.errors().add(e.getMessage());
        //}

        //return responseDto;


    public long convert(long amount, String fromCurrency, String toCurrency) {


        if(!fromCurrency.equals("RUR") && !toCurrency.equals("RUR")) {
            amount = convert(amount, fromCurrency, "RUR");
            fromCurrency = "RUR";
        }


        if(fromCurrency.equals("RUR")) {
            amount = Math.round(amount / getRate(toCurrency));
        }
        else {
            amount = Math.round(amount * getRate(fromCurrency));
        }

        return amount;
    }

    public List<ExchangeRateDto> getExchangeRates() {
        List<ExchangeRate>  exchangeRates = exchangeRepository.findAll();
        return exchangeRates.stream().map(exchangeRateMapper::toExchangeRateDto).toList();
    }

    private double getRate(String toCurrency) {
        return exchangeRepository.findByCurrencyCode(toCurrency).getRate();
    }
}
