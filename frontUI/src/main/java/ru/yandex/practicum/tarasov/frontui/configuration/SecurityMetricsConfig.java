package ru.yandex.practicum.tarasov.frontui.configuration;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

@Configuration
public class SecurityMetricsConfig {

    private final Counter successCounter;
    private final Counter failureCounter;

    public SecurityMetricsConfig(MeterRegistry meterRegistry) {
        successCounter = Counter.builder("user.logins.success")
                .description("Successful login")
                .register(meterRegistry);
        failureCounter = Counter.builder("user.logins.failure")
                .description("Failed login")
                .register(meterRegistry);
    }

    @EventListener
    public void onSuccess() {
        successCounter.increment();
    }

    @EventListener
    public void onFailure() {
        failureCounter.increment();
    }
}
