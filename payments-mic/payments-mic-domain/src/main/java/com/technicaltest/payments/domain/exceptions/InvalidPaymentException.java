package com.technicaltest.payments.domain.exceptions;

import com.technicaltest.payments.domain.values.PaymentId;
import lombok.NonNull;

public class InvalidPaymentException extends BusinessException {

    public InvalidPaymentException(@NonNull PaymentId paymentId) {
        super("The payment " + paymentId.getId().toString() + " is invalid.");
    }
}
