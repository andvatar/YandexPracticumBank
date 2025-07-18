package ru.yandex.practicum.tarasov.frontui.client.accounts.dto;

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
