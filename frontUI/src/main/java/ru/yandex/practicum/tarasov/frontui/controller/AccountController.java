package ru.yandex.practicum.tarasov.frontui.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.yandex.practicum.tarasov.frontui.DTO.CashDto;
import ru.yandex.practicum.tarasov.frontui.DTO.ResponseDto;
import ru.yandex.practicum.tarasov.frontui.service.AccountService;

@Controller
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(path = "/user/{login}/сash",
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String getPutCash(@PathVariable("login") String login,
//                             @RequestParam MultiValueMap<String,String> paramMap,
                             @ModelAttribute CashDto cashDto,
                             RedirectAttributes redirectAttributes) {

        System.out.println(login);

        cashDto.setUsername(login);
        ResponseDto responseDto = accountService.getPutCash(cashDto);

        redirectAttributes.addFlashAttribute("cashErrors", responseDto.errors());

        return "redirect:/main";
    }
}
