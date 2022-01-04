package com.technicaltest.payments.domain.exceptions;

import com.technicaltest.payments.domain.entities.PaymentError;

public abstract class PaymentException extends BusinessException {

    public PaymentException(String message) {
        super(message);
    }

    public PaymentException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract PaymentError toError();
}
