package com.technicaltest.payments.jpaadapter.repositories;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import org.springframework.stereotype.Repository;

import com.technicaltest.payments.application.repositories.AccountRepository;
import com.technicaltest.payments.domain.entities.Account;
import com.technicaltest.payments.domain.values.AccountId;
import com.technicaltest.payments.jpaadapter.entities.AccountJPA;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@RequiredArgsConstructor
public class AccountRepositoryJpaAdapter implements AccountRepository {

    @NonNull
    private final EntityManager entityManager;

    @Override
    public Account retrieveAndLock(AccountId accountId) {
        return toEntity(entityManager.find(AccountJPA.class, accountId.getId(),
            LockModeType.PESSIMISTIC_WRITE));
    }

    @Override
    public void update(@NonNull Account account) {
        entityManager.merge(toJPA(account));
    }

    private AccountJPA toJPA(Account account) {
        return AccountJPA.builder()
            .accountId(account.getAccountId().getId())
            .name(account.getName())
            .email(account.getEmail())
            .birthdate(account.getBirthdate())
            .lastPaymentDate(account.getLastPaymentDate())
            .build();
    }

    private Account toEntity(AccountJPA accountJPA) {
        return Optional.ofNullable(accountJPA)
            .map(account -> Account.builder()
                .accountId(new AccountId(account.getAccountId()))
                .name(account.getName())
                .email(account.getEmail())
                .birthdate(account.getBirthdate())
                .lastPaymentDate(account.getLastPaymentDate())
                .build())
            .orElse(null);
    }
    
}
