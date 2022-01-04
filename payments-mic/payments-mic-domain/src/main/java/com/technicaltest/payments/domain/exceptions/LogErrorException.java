package com.technicaltest.payments.domain.exceptions;

import com.technicaltest.payments.domain.values.PaymentId;

public class LogErrorException extends RuntimeException {

    public LogErrorException(PaymentId paymentId, Throwable cause) {
        super("Error trying to log the problem with payment: " + paymentId.getId().toString(), cause);
    }
}
