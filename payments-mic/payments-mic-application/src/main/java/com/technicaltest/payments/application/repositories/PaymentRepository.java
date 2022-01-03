package com.technicaltest.payments.application.repositories;

import com.technicaltest.payments.domain.entities.Payment;
import com.technicaltest.payments.domain.values.PaymentId;

public interface PaymentRepository {

    void save(Payment payment);

    boolean exists(PaymentId paymentId);
}
