package ru.yandex.practicum.tarasov.frontui.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class User implements UserDetails {

    private BigDecimal id;

    private String username;

    private String password;

    private LocalDate birthDate;

    private String lastName;

    private String firstName;

    private String email;

    private Set<Authority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
