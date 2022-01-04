package com.technicaltest.payments.application.services;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.technicaltest.payments.application.repositories.AccountRepository;
import com.technicaltest.payments.application.repositories.PaymentGatewayRepository;
import com.technicaltest.payments.application.repositories.PaymentRepository;
import com.technicaltest.payments.application.usecases.RegisterAlreadyValidatedPaymentUseCase;
import com.technicaltest.payments.application.usecases.RegisterNotValidatedPaymentUseCase;
import com.technicaltest.payments.domain.entities.Account;
import com.technicaltest.payments.domain.entities.Payment;
import com.technicaltest.payments.domain.exceptions.AccountNotFoundException;
import com.technicaltest.payments.domain.exceptions.InvalidPaymentException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentsService implements RegisterAlreadyValidatedPaymentUseCase, RegisterNotValidatedPaymentUseCase {

    @NonNull
    private final AccountRepository accountRepository;
    @NonNull
    private final PaymentRepository paymentRepository;
    @NonNull
    private final PaymentGatewayRepository paymentGatewayRepository;

    @Transactional
    @Override
    public void saveValidPayment(Payment payment) {
        if(!paymentRepository.exists(payment.getPaymentId())) {
            savePayment(payment);
        } else {
            log.info("Payment {} already exists", payment.getPaymentId());
        }
    }

    @Transactional
    @Override
    public void validateAndSavePayment(Payment payment) {
        if(!paymentRepository.exists(payment.getPaymentId())) {
            saveIfValidPayment(payment);
        } else {
            log.info("Payment {} already exists", payment.getPaymentId());
        }
    }

    private void savePayment(Payment payment) {
        Account account = accountRepository.retrieveAndLock(payment.getAccountId());
        if (Objects.isNull(account)) {
            throw new AccountNotFoundException(payment.getAccountId());
        }
        paymentRepository.save(payment);

        account.addPayment(payment);
        accountRepository.update(account);
    }

    private void saveIfValidPayment(Payment payment) {
        if(paymentGatewayRepository.validPayment(payment)) {
            savePayment(payment);
        } else {
            throw new InvalidPaymentException(payment.getPaymentId());
        }
    }
}
