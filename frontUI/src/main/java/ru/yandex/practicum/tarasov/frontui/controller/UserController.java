package ru.yandex.practicum.tarasov.frontui.controller;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.yandex.practicum.tarasov.frontui.DTO.ResponseDto;
import ru.yandex.practicum.tarasov.frontui.client.accounts.dto.*;
import ru.yandex.practicum.tarasov.frontui.entity.User;
import ru.yandex.practicum.tarasov.frontui.service.UserAccountsService;

import java.util.List;

@Controller
public class UserController {

    private final UserAccountsService userAccountsService;

    public UserController(
                          UserAccountsService userAccountsService) {
        this.userAccountsService = userAccountsService;
    }

    @GetMapping("/signup")
    public String getSignup(Model model) {
        model.addAttribute("userDto", new SignupRequestDto());
        return "signup";
    }

    @GetMapping("/main")
    public String getMain(Model model,
                          @AuthenticationPrincipal User user) {
        model.addAttribute("login", user.getUsername());
        model.addAttribute("last_name", user.getLastName());
        model.addAttribute("first_name", user.getFirstName());
        model.addAttribute("birthdate", user.getBirthDate());

        UserAccountsDto userAccountsDto = userAccountsService.getUserAccounts(user.getUsername());
        List<UserDto> users = userAccountsService.getOtherUsers(user.getUsername());

        model.addAttribute("accounts", userAccountsDto.accounts());
        model.addAttribute("currency", userAccountsDto.accounts().stream().map(AccountDto::getCurrency).toList());
        model.addAttribute("users", users);

        return "main";
    }

    @PostMapping("/signup")
    public String postSignup(@ModelAttribute("userDto") SignupRequestDto signupRequestDto,
                             RedirectAttributes redirectAttributes) {

        ResponseDto dto = userAccountsService.signup(signupRequestDto);
        if(dto.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", dto.errors());
            return "redirect:/signup";
        }
        else {
            return "redirect:/login";
        }
    }

    @PostMapping("/user/{login}/editPassword")
    public String changePassword(@PathVariable("login") String login,
                                 @RequestParam("password") String password,
                                 @RequestParam("confirm_password") String confirm_password,
                                 RedirectAttributes redirectAttributes) {
        ResponseDto dto = userAccountsService.changePassword(new ChangePasswordDto(login, password, confirm_password));

        redirectAttributes.addFlashAttribute("passwordErrors", dto.errors());
        return "redirect:/main";
    }

    @PostMapping(path = "/user/{login}/editUserAccounts",
    consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String editUserAccounts(@PathVariable("login") String login,
                                   @ModelAttribute ChangeUserAccountsDto changeUserAccountsDto,
                                   RedirectAttributes redirectAttributes) {
        ResponseDto dto = userAccountsService.editUserAccounts(changeUserAccountsDto, login);
        redirectAttributes.addFlashAttribute("userAccountsErrors", dto.errors());
        return "redirect:/main";
    }

}
