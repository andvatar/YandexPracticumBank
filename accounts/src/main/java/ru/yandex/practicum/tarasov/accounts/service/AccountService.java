package ru.yandex.practicum.tarasov.accounts.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import ru.yandex.practicum.tarasov.accounts.DTO.CashDto;
import ru.yandex.practicum.tarasov.accounts.DTO.ResponseDto;
import ru.yandex.practicum.tarasov.accounts.DTO.TransferDto;
import ru.yandex.practicum.tarasov.accounts.entity.Account;
import ru.yandex.practicum.tarasov.accounts.entity.User;
import ru.yandex.practicum.tarasov.accounts.repository.AccountRepository;
import ru.yandex.practicum.tarasov.accounts.repository.UserRepository;

import java.util.Optional;

@Service
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public ResponseDto getPutCash(CashDto  cashDto) {
        log.info("getPutCash started with cashDto={}", cashDto);
        ResponseDto responseDto = new ResponseDto();

        Optional<User> optionalUser = userRepository.findByUsername(cashDto.getUsername());
        if(optionalUser.isEmpty()) {
            responseDto.errors().add("User not found");
            log.warn("getPutCash returned empty user");
            return responseDto;
        }

        Optional<Account> optionalAccount = optionalUser
                .get()
                .getAccounts()
                .stream()
                .filter(a -> a.getCurrency().getCode().equals(cashDto.getCurrency()))
                .findFirst();

        if(optionalAccount.isEmpty()) {
            responseDto.errors().add("Account not found");
            log.warn("getPutCash returned empty account");
            return responseDto;
        }

        Account account = optionalAccount.get();

        long newBalance = account.getBalance();

        if(cashDto.getAction().equals("PUT")) {
            newBalance += cashDto.getValue();
        } else if(cashDto.getAction().equals("GET")) {
            newBalance -= cashDto.getValue();
        } else {
            responseDto.errors().add("Invalid action");
            log.warn("getPutCash returned invalid action");
            return responseDto;
        }

        if(newBalance < 0) {
            responseDto.errors().add("Not enough money");
            log.warn("getPutCash returned not enough money");
            return responseDto;
        }

        account.setBalance(newBalance);
        try {
            accountRepository.save(account);
        }
        catch(Exception e) {
            responseDto.errors().add("Error saving account");
            log.error("getPutCash returned error saving account", e);
        }

        return responseDto;
    }

    @Transactional
    public ResponseDto transfer(TransferDto transferDto) {
        log.info("transfer started with transferDto={}", transferDto);
        ResponseDto responseDto = new ResponseDto();

        ResponseDto getResponseDto = getPutCash(
                new CashDto(
                    transferDto.fromLogin(),
                    transferDto.fromCurrency(),
                    transferDto.fromAmount(),
                    "GET"));

        responseDto.errors().addAll(getResponseDto.errors());

        ResponseDto putResponseDto = getPutCash(
                new CashDto(
                        transferDto.toLogin(),
                        transferDto.toCurrency(),
                        transferDto.toAmount(),
                        "PUT"));

        responseDto.errors().addAll(putResponseDto.errors());

        if(responseDto.hasErrors()) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("transfer returned errors {}", responseDto.errors());
        }

        return  responseDto;
    }
}
