package ru.yandex.practicum.tarasov.accounts.DTO;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ChangeUserAccountsDto {
    //private String username;
    private String lastName;
    private String firstName;
    private LocalDate birthDate;
    private List<String> accounts = new ArrayList<>();
}
