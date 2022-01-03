package com.technicaltest.payments.application.repositories;

import com.technicaltest.payments.domain.entities.Account;
import com.technicaltest.payments.domain.values.AccountId;

public interface AccountRepository {

    Account retrieveAndLock(AccountId accountId);

    void update(Account account);
}
