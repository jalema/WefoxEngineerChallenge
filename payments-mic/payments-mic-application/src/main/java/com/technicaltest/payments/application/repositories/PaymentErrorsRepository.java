package com.technicaltest.payments.application.repositories;

import com.technicaltest.payments.domain.entities.PaymentError;

public interface PaymentErrorsRepository {
    void save(PaymentError error);
}
