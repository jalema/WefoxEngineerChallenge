package com.technicaltest.payments.domain.exceptions;

import com.technicaltest.payments.domain.entities.PaymentError;

public abstract class PaymentException extends BusinessException {

    protected PaymentException(String message) {
        super(message);
    }

    public abstract PaymentError toError();
}
