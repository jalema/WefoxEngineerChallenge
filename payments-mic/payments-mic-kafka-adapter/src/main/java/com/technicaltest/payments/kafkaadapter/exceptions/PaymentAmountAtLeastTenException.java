package com.technicaltest.payments.kafkaadapter.exceptions;

public class PaymentAmountAtLeastTenException extends BusinessException {

    public PaymentAmountAtLeastTenException(String paymentId, Integer amount) {
        super("Payment: " + paymentId + " | Amount need to be at least 10 but is " + amount);
    }
}
