package ru.yandex.practicum.tarasov.accounts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.tarasov.accounts.entity.Authority;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    Optional<Authority> findByName(String name);
    boolean existsByName(String name);
}
