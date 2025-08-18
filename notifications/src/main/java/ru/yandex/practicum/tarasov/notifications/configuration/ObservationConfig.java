package ru.yandex.practicum.tarasov.notifications.configuration;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationPredicate;
import io.micrometer.observation.ObservationView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.observation.ServerRequestObservationContext;

//https://github.com/spring-projects/spring-boot/issues/34801

@Configuration
public class ObservationConfig {

    @Bean
    ObservationPredicate noActuatorServerObservations() {
        return (name, context) ->
        {
            Observation.Context root = getRoot(context);
            if (root instanceof ServerRequestObservationContext serverContext) {
                return !serverContext.getCarrier().getRequestURI().startsWith("/actuator");
            } else {
                return true;
            }
        };
    }

    private Observation.Context getRoot(Observation.Context current) {
        ObservationView parent = current.getParentObservation();
        if (parent == null) {
            return current;
        } else {
            return getRoot((Observation.Context) parent.getContextView());
        }
    }
}
