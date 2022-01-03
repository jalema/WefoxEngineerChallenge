package com.technicaltest.payments.domain.exceptions;

import com.technicaltest.payments.domain.values.AccountId;

public class AccountNotFoundException extends BusinessException{

    public AccountNotFoundException(AccountId accountId) {
        super("Account " + accountId.getId() + " not found");
    }
}
