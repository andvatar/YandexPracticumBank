package ru.yandex.practicum.tarasov.cash.configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

// https://www.edstem.com/blog/jwt-tokens-microservice-architectures-spring-cloud/

public class JwtTokenPropagator implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getCredentials() instanceof Jwt jwtToken) {
            template.header("Authorization", "Bearer " + jwtToken.getTokenValue());
        }
    }
}
