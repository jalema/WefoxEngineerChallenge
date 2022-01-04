package com.technicaltest.payments.application.repositories;

import com.technicaltest.payments.domain.entities.Payment;

public interface PaymentGatewayRepository {

    boolean validPayment(Payment payment);
}
