package ru.yandex.practicum.tarasov.cash.configuration;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class FeignConfig {
    @Bean
    public RequestInterceptor jwtTokenPropagator() {
        return new JwtTokenPropagator();
    }
}
