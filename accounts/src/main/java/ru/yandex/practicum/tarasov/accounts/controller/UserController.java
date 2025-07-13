package ru.yandex.practicum.tarasov.accounts.controller;

import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.tarasov.accounts.DTO.ChangePasswordDto;
import ru.yandex.practicum.tarasov.accounts.DTO.CreateUserResponseDto;
import ru.yandex.practicum.tarasov.accounts.DTO.UserDto;
import ru.yandex.practicum.tarasov.accounts.entity.User;
import ru.yandex.practicum.tarasov.accounts.service.CustomUserDetailsService;
import ru.yandex.practicum.tarasov.accounts.service.UserService;

import java.time.LocalDateTime;


@RestController
public class UserController {

    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;

    public UserController(UserService userService,
                          CustomUserDetailsService customUserDetailsService) {
        this.userService = userService;
        this.customUserDetailsService = customUserDetailsService;
    }

    /*@GetMapping("/signup")
    public String getSignup(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "signup";
    }*/

    @PostMapping("/signup")
    public CreateUserResponseDto postSignup(@Valid @RequestBody() UserDto userDto) {
        userService.createUser(userDto);
        return new CreateUserResponseDto(LocalDateTime.now(), "Ok", null);
    }

    @GetMapping("/user/{name}")
    public UserDetails getUserDetailsByName(@PathVariable("name") String name) {
        return customUserDetailsService.loadUserByUsername(name);
    }

    @PostMapping("/changePassword")
    public User changePassword(@RequestBody ChangePasswordDto changePasswordDto)
    {
        return userService.changePassword(changePasswordDto);
    }
}
