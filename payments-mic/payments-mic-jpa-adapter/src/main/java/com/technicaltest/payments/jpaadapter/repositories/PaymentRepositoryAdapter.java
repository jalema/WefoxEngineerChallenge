package com.technicaltest.payments.jpaadapter.repositories;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.technicaltest.payments.application.repositories.PaymentRepository;
import com.technicaltest.payments.domain.entities.Payment;
import com.technicaltest.payments.domain.values.PaymentId;
import com.technicaltest.payments.jpaadapter.entities.PaymentJPA;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@RequiredArgsConstructor
public class PaymentRepositoryAdapter implements PaymentRepository {

    @NonNull
    private final EntityManager entityManager;

    @Override
    public void save(Payment payment) {
        entityManager.persist(toJPA(payment));
    }

    @Override
    public boolean exists(PaymentId paymentId) {
        Query query = entityManager.createQuery("SELECT COUNT(p) FROM payments p WHERE p.paymentId = :paymentId");
        query.setParameter("paymentId", paymentId.getId().toString());
        return Long.valueOf(1).equals(query.getSingleResult());
    }

    private PaymentJPA toJPA(Payment payment) {
        return PaymentJPA.builder()
            .paymentId(payment.getPaymentId().getId().toString())
            .accountId(payment.getAccountId().getId())
            .paymentType(payment.getPaymentType().toString())
            // TODO .creditCard()
            .amount(payment.getAmount())
            .createdOn(payment.getCreatedOn())
            .build();
    }
}
