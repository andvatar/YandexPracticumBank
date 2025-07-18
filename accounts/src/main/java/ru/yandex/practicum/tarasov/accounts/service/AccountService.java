package ru.yandex.practicum.tarasov.accounts.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.tarasov.accounts.DTO.CashDto;
import ru.yandex.practicum.tarasov.accounts.DTO.ResponseDto;
import ru.yandex.practicum.tarasov.accounts.entity.Account;
import ru.yandex.practicum.tarasov.accounts.entity.User;
import ru.yandex.practicum.tarasov.accounts.repository.AccountRepository;
import ru.yandex.practicum.tarasov.accounts.repository.CurrencyRepository;
import ru.yandex.practicum.tarasov.accounts.repository.UserRepository;

import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository, CurrencyRepository currencyRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.currencyRepository = currencyRepository;
    }

    public ResponseDto getPutCash(CashDto  cashDto) {
        ResponseDto responseDto = new ResponseDto();

        Optional<User> optionalUser = userRepository.findByUsername(cashDto.getUsername());
        if(optionalUser.isEmpty()) {
            responseDto.errors().add("User not found");
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
            return responseDto;
        }

        if(newBalance < 0) {
            responseDto.errors().add("Not enough money");
            return responseDto;
        }

        account.setBalance(newBalance);
        accountRepository.save(account);

        return responseDto;
    }
}
