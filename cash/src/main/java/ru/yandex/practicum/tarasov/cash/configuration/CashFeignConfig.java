package ru.yandex.practicum.tarasov.cash.configuration;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class CashFeignConfig {
    @Bean
    public RequestInterceptor jwtTokenPropagator() {
        return new JwtTokenPropagator();
    }
}
