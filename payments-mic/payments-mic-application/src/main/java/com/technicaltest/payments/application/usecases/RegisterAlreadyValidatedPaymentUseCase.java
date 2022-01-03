package com.technicaltest.payments.application.usecases;

import com.technicaltest.payments.domain.entities.Payment;

public interface RegisterAlreadyValidatedPaymentUseCase {
    void saveValidPayment(Payment payment);
}
