package ru.yandex.practicum.tarasov.frontui.configuration;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

@Configuration
public class SecurityMetricsConfig {

    //private final MeterRegistry meterRegistry;
    private final Counter successCounter;
    private final Counter failureCounter;

    public SecurityMetricsConfig(MeterRegistry meterRegistry) {
        //this.meterRegistry = meterRegistry;
        successCounter = Counter.builder("user.logins.success")
                .description("Successful login")
                .register(meterRegistry);
        failureCounter = Counter.builder("user.logins.failure")
                .description("Failed login")
                .register(meterRegistry);
    }

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent event) {
        //String username = event.getAuthentication().getName();
        /*Counter.builder("user.logins.success")
                .description("Successful login")
                .tags("username", username)
                .register(meterRegistry)
                .increment();*/
        successCounter.increment();
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent event) {
        //String username = event.getAuthentication().getName();
        /*Counter.builder("user.logins.failure")
                .description("Failed login")
                .tags("username", username)
                .register(meterRegistry)
                .increment();*/
        failureCounter.increment();
    }
}
