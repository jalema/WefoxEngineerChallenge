package com.technicaltest.payments.application.usecases;

import com.technicaltest.payments.domain.entities.Payment;

public interface RegisterNotValidatedPaymentUseCase {

    void validateAndSavePayment(Payment payment);
}
