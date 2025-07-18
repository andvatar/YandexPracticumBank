package ru.yandex.practicum.tarasov.accounts.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.tarasov.accounts.DTO.*;
import ru.yandex.practicum.tarasov.accounts.entity.Account;
import ru.yandex.practicum.tarasov.accounts.entity.Authority;
import ru.yandex.practicum.tarasov.accounts.entity.User;
import ru.yandex.practicum.tarasov.accounts.repository.AccountRepository;
import ru.yandex.practicum.tarasov.accounts.repository.AuthorityRepository;
import ru.yandex.practicum.tarasov.accounts.repository.CurrencyRepository;
import ru.yandex.practicum.tarasov.accounts.repository.UserRepository;


import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final CurrencyRepository currencyRepository;
    private final AccountRepository accountRepository;


    public UserService(UserRepository userRepository,
                       AuthorityRepository authorityRepository,
                       CurrencyRepository currencyRepository,
                       AccountRepository accountRepository,
                       PasswordEncoder passwordEncoder,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.currencyRepository = currencyRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public ResponseDto createUser(SignupRequestDto signupRequestDto) {
        ResponseDto responseDto = new ResponseDto();

        User user = userMapper.signupRequestDtoToUser(signupRequestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Optional<Authority> optionalAuthority = authorityRepository.findByName("USER");
        if(optionalAuthority.isEmpty()) {
            responseDto.errors().add("Authority 'USER' not found");
            return responseDto;
        }
        user.setAuthorities(Set.of(optionalAuthority.get()));
        userRepository.save(user);

        return responseDto;
    }

    public ResponseDto changePassword(ChangePasswordDto changePasswordDto) {
        ResponseDto responseDto = new ResponseDto();
        if(!changePasswordDto.password().equals(changePasswordDto.confirmPassword())) {
            responseDto.errors().add("Passwords do not match");
        }

        Optional<User> optionalUser = userRepository.findByUsername(changePasswordDto.login());

        if(optionalUser.isEmpty()) {
            responseDto.errors().add("Username not found");
        }

        if(!responseDto.hasErrors()) {
            User user = optionalUser.get();
            user.setPassword(passwordEncoder.encode(changePasswordDto.password()));
            userRepository.save(user);
        }

        return responseDto;
    }

    public UserAccountsDto getUserAccounts(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.userToUserAccountsDto(user);
    }

    @Transactional
    public ResponseDto updateUserAccounts(ChangeUserAccountsDto changeUserAccountsDto, String login) {
        ResponseDto responseDto = new ResponseDto();

        Optional<User> userOptional = userRepository.findByUsername(login);

        if(userOptional.isEmpty()) {
            responseDto.errors().add("User not found");
            return responseDto;
        }

        User user = userOptional.get();

        if(changeUserAccountsDto.getBirthDate() != null) {
            if (Period.between(changeUserAccountsDto.getBirthDate(), LocalDate.now()).getYears() < 18) {
                responseDto.errors().add("You must be at least 18 years old");
            }
            else {
                user.setBirthDate(changeUserAccountsDto.getBirthDate());
            }
        }

        if(changeUserAccountsDto.getFirstName() != null) {
            user.setFirstName(changeUserAccountsDto.getFirstName());
        }

        if(changeUserAccountsDto.getLastName() != null) {
            user.setLastName(changeUserAccountsDto.getLastName());
        }

        List<Account> accountsToClose = new ArrayList<>();
        List<Account> accountsToOpen = new ArrayList<>();

        for (Account account : user.getAccounts()) {
            if(changeUserAccountsDto.getAccounts().stream().noneMatch(s -> s.equals(account.getCurrency().getCode()))) {
                if(account.getBalance() > 0) {
                    responseDto.errors().add("Cannot close the " + account.getCurrency().getCode() + " account because the balance is greater than 0");
                } else {
                    accountsToClose.add(account);
                }
            }
        }

        for (String curr : changeUserAccountsDto.getAccounts()) {
            if(user.getAccounts().stream().noneMatch(a -> a.getCurrency().getCode().equals(curr))) {
                accountsToOpen.add(new Account(0, currencyRepository.findByCode(curr)));
            }
        }

        if(!responseDto.hasErrors()) {
            user.getAccounts().addAll(accountsToOpen);
            user.getAccounts().removeAll(accountsToClose);
            accountRepository.deleteAll(accountsToClose);
            userRepository.save(user);
        }

        return responseDto;
    }
}
