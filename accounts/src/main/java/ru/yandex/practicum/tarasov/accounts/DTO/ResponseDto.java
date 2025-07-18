package ru.yandex.practicum.tarasov.accounts.DTO;

import java.util.ArrayList;
import java.util.List;

public record ResponseDto(List<String> errors) {

    public ResponseDto() {
        this(new ArrayList<>());
    }

    public boolean hasErrors() {
        return errors != null && !errors.isEmpty();
    }
}
