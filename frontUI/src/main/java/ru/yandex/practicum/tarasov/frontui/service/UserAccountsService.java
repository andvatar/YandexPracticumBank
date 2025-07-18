package ru.yandex.practicum.tarasov.frontui.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.tarasov.frontui.DTO.ResponseDto;
import ru.yandex.practicum.tarasov.frontui.client.accounts.AccountsClient;
import ru.yandex.practicum.tarasov.frontui.client.accounts.dto.*;

@Service
public class UserAccountsService {
    private final AccountsClient accountsClient;
    private final ObjectMapper objectMapper;

    public UserAccountsService(AccountsClient accountsClient,
                               ObjectMapper objectMapper) {
        this.accountsClient = accountsClient;
        this.objectMapper = objectMapper;
    }

    public UserAccountsDto getUserAccounts(String username) {
        return accountsClient.getUserAccounts(username);
    }

    public ResponseDto changePassword(ChangePasswordDto changePasswordDto) {
        return accountsClient.changePassword(changePasswordDto);
    }

    public ResponseDto signup(SignupRequestDto signupRequestDto) {
        try {
            return accountsClient.postSignup(signupRequestDto);
        }
        catch (FeignException.BadRequest e) {
            System.out.println(e.contentUTF8());
            try {
                return objectMapper.readValue(e.contentUTF8(), ResponseDto.class);
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    public ResponseDto editUserAccounts(ChangeUserAccountsDto changeUserAccountsDto,
                                        String login) {
        return accountsClient.editUserAccounts(changeUserAccountsDto, login);
    }
}
