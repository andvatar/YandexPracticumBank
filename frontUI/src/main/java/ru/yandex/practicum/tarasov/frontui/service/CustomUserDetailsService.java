package ru.yandex.practicum.tarasov.frontui.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.tarasov.frontui.client.accounts.AccountsClient;
import ru.yandex.practicum.tarasov.frontui.entity.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final AccountsClient accountsClient;

    public CustomUserDetailsService(AccountsClient accountsClient) {
        this.accountsClient = accountsClient;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountsClient.getUserDetailsByName(username);
    }
}
