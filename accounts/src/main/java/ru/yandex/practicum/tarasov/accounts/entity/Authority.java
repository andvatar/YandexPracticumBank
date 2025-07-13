package ru.yandex.practicum.tarasov.accounts.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.math.BigDecimal;

@Entity
@Table(name = "authorities")
@NoArgsConstructor
public class Authority implements GrantedAuthority {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigDecimal id;
    @Column(name = "authority")
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }

    public Authority(String name) {
        this.name = name;
    }

}
