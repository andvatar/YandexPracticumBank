package ru.yandex.practicum.tarasov.frontui.client.accounts;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.tarasov.frontui.DTO.ResponseDto;
import ru.yandex.practicum.tarasov.frontui.client.accounts.dto.*;
import ru.yandex.practicum.tarasov.frontui.configuration.OAuthFeignConfig;
import ru.yandex.practicum.tarasov.frontui.entity.User;

import java.util.List;

@FeignClient(name = "accounts", configuration = OAuthFeignConfig.class)
public interface AccountsClient {

    @PostMapping("/signup")
    ResponseDto postSignup(@Valid @RequestBody() SignupRequestDto signupRequestDto);

    @GetMapping("/user/{name}")
    User getUserDetailsByName(@PathVariable("name") String name);

    @PostMapping("/changePassword")
    ResponseDto changePassword(@RequestBody ChangePasswordDto changePasswordDto);

    @GetMapping("/user/{username}/accounts")
    UserAccountsDto getUserAccounts(@PathVariable("username") String username);

    @PostMapping("/user/{login}/accounts")
    ResponseDto editUserAccounts(@RequestBody ChangeUserAccountsDto changeUserAccountsDto,
                                 @PathVariable String login);

    @GetMapping("user/{login}/others")
    List<UserDto> getOtherUsers(@PathVariable("login") String login);

}
