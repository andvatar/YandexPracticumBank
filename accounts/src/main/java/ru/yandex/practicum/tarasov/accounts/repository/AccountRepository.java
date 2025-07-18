package ru.yandex.practicum.tarasov.accounts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.tarasov.accounts.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
