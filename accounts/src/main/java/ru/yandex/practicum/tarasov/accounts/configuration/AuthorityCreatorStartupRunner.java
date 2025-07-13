package ru.yandex.practicum.tarasov.accounts.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.tarasov.accounts.entity.Authority;
import ru.yandex.practicum.tarasov.accounts.repository.AuthorityRepository;


@Component
public class AuthorityCreatorStartupRunner implements CommandLineRunner {
    private final AuthorityRepository authorityRepository;

    public AuthorityCreatorStartupRunner(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(!authorityRepository.existsByName("USER")) {
            authorityRepository.save(new Authority("USER"));
        }
    }
}
