package ru.yandex.practicum.tarasov.frontui.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Authority implements GrantedAuthority {

    private BigDecimal id;
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }

}
