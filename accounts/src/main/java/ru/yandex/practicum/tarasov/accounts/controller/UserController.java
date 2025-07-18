package ru.yandex.practicum.tarasov.accounts.controller;

import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.tarasov.accounts.DTO.*;
import ru.yandex.practicum.tarasov.accounts.service.CustomUserDetailsService;
import ru.yandex.practicum.tarasov.accounts.service.UserService;

@RestController
public class UserController {

    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;

    public UserController(UserService userService,
                          CustomUserDetailsService customUserDetailsService) {
        this.userService = userService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/signup")
    public ResponseDto postSignup(@Valid @RequestBody() SignupRequestDto signupRequestDto) {
        return userService.createUser(signupRequestDto);
    }

    @GetMapping("/user/{name}")
    public UserDetails getUserDetailsByName(@PathVariable("name") String name) {
        return customUserDetailsService.loadUserByUsername(name);
    }

    @PostMapping("/changePassword")
    public ResponseDto changePassword(@RequestBody ChangePasswordDto changePasswordDto)
    {
        return userService.changePassword(changePasswordDto);
    }

    @GetMapping("/user/{login}/accounts")
    public UserAccountsDto getUserAccounts(@PathVariable("login") String username) {
        return userService.getUserAccounts(username);
    }

    @PostMapping("/user/{login}/accounts")
    public ResponseDto editUserAccounts(@RequestBody ChangeUserAccountsDto changeUserAccountsDto,
                                        @PathVariable String login) {
        return userService.updateUserAccounts(changeUserAccountsDto, login);
    }
}
