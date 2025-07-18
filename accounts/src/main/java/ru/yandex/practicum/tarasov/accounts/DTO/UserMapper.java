package ru.yandex.practicum.tarasov.accounts.DTO;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.tarasov.accounts.entity.Account;
import ru.yandex.practicum.tarasov.accounts.entity.Currency;
import ru.yandex.practicum.tarasov.accounts.entity.User;
import ru.yandex.practicum.tarasov.accounts.repository.CurrencyRepository;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;


@Mapper(componentModel = "spring", uses = CurrencyRepository.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class UserMapper {

    private CurrencyRepository currencyRepository;

    public abstract User signupRequestDtoToUser(SignupRequestDto signupRequestDto);

    public abstract User userAccountDtoToUser(UserAccountsDto userAccountsDto);

    @Mapping(source = "accounts", target = "accounts", qualifiedByName = "setAccountsDto")
    public abstract UserAccountsDto userToUserAccountsDto(User user);

    @Named(value = "setAccountsDto")
    public List<AccountDto> setAccountsDto(List<Account> accounts) {
        List<Currency> currencies = currencyRepository.findAll();
        return currencies.stream()
                .map(c ->
                        accounts.stream()
                                .filter(a -> a.getCurrency().equals(c))
                                .map(account -> new AccountDto(account.getBalance(), true, c))
                                .findFirst()
                                .orElse(new AccountDto(0, false, c))
                )
        .toList();
    }

    @Autowired
    public void setCurrencyRepository(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }
}
