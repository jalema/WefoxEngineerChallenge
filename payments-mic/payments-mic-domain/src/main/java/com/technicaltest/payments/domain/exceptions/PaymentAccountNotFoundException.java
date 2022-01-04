package com.technicaltest.payments.domain.exceptions;

import com.technicaltest.payments.domain.entities.PaymentError;
import com.technicaltest.payments.domain.values.AccountId;
import com.technicaltest.payments.domain.values.ErrorType;
import com.technicaltest.payments.domain.values.PaymentId;
import lombok.NonNull;

public class PaymentAccountNotFoundException extends PaymentException {

    private final PaymentId paymentId;

    private final AccountId accountId;

    public PaymentAccountNotFoundException(@NonNull PaymentId paymentId, @NonNull AccountId accountId) {
        super("Payment " + paymentId.getId().toString() + " | Account " + accountId.getId() + " not found");
        this.paymentId = paymentId;
        this.accountId = accountId;
    }

    @Override
    public PaymentError toError() {
        return PaymentError.builder()
            .paymentId(paymentId)
            .error(ErrorType.DATABASE)
            .description("The account " + accountId.getId() + " does not exists")
            .build();
    }
}
