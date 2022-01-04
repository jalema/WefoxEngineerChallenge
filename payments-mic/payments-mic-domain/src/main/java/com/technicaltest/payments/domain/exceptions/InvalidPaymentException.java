package com.technicaltest.payments.domain.exceptions;

import com.technicaltest.payments.domain.entities.PaymentError;
import com.technicaltest.payments.domain.values.ErrorType;
import com.technicaltest.payments.domain.values.PaymentId;
import lombok.NonNull;

public class InvalidPaymentException extends PaymentException {

    private final PaymentId paymentId;

    public InvalidPaymentException(@NonNull PaymentId paymentId) {
        super("The payment " + paymentId.getId().toString() + " is invalid.");
        this.paymentId = paymentId;
    }

    @Override
    public PaymentError toError() {
        return PaymentError.builder()
            .paymentId(paymentId)
            .error(ErrorType.NETWORK)
            .description("The payment gateway consider this as invalid")
            .build();
    }
}
