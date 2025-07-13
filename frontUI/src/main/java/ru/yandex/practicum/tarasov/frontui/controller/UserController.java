package ru.yandex.practicum.tarasov.frontui.controller;

import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.yandex.practicum.tarasov.frontui.DTO.ChangePasswordDto;
import ru.yandex.practicum.tarasov.frontui.DTO.CreateUserResponseDto;
import ru.yandex.practicum.tarasov.frontui.DTO.UserDto;
import ru.yandex.practicum.tarasov.frontui.entity.User;

@Controller
public class UserController {

    private final RestTemplate restTemplate;

    public UserController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/signup")
    public String getSignup(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "signup";
    }

    @GetMapping("/main")
    public String getMain(Model model,
                          @AuthenticationPrincipal User user) {
        System.out.println(user.getUsername());
        model.addAttribute("login", user.getUsername());
        model.addAttribute("last_name", user.getLastName());
        model.addAttribute("first_name", user.getFirstName());
        model.addAttribute("birthdate", user.getBirthDate());

        return "main";
    }

    @PostMapping("/signup")
    public String postSignup(@Valid @ModelAttribute("userDto") UserDto userDto) throws BadRequestException {
        //userService.createUser(userDto);
        //CreateUserResponseDto resp = accountsClient.signup();

        CreateUserResponseDto resp = restTemplate.postForObject("http://accounts/signup", userDto, CreateUserResponseDto.class);
        if(resp == null) {
            throw new BadRequestException("No response");
        }
        if(resp.errors() != null) {
            throw new BadRequestException(resp.errors().toString());
        }
        return "redirect:/login";

    }

    @PostMapping("/user/{login}/editPassword")
    public String changePassword(@PathVariable("login") String login,
                                 @RequestParam("password") String password,
                                 @RequestParam("confirm_password") String confirm_password) {
        ChangePasswordDto dto = new ChangePasswordDto(login, password, confirm_password);
        restTemplate.postForObject("http://accounts/changePassword", dto, User.class);

        return "redirect:/main";
    }
}
