package ru.yandex.practicum.tarasov.exchangegenerator.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@Profile("!IntegrationTest")
public class SchedulingConfig {
}
