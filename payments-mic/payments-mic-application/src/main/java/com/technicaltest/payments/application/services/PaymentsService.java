package com.technicaltest.payments.application.services;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.technicaltest.payments.application.repositories.AccountRepository;
import com.technicaltest.payments.application.repositories.PaymentRepository;
import com.technicaltest.payments.application.usecases.RegisterAlreadyValidatedPaymentUseCase;
import com.technicaltest.payments.domain.entities.Account;
import com.technicaltest.payments.domain.entities.Payment;
import com.technicaltest.payments.domain.exceptions.AccountNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentsService implements RegisterAlreadyValidatedPaymentUseCase {

    @NonNull
    private final AccountRepository accountRepository;
    @NonNull
    private final PaymentRepository paymentRepository;

    @Transactional
    @Override
    public void saveValidPayment(Payment payment) {
        if(!paymentRepository.exists(payment.getPaymentId())) {
            Account account = accountRepository.retrieveAndLock(payment.getAccountId());
            if (Objects.isNull(account)) {
                throw new AccountNotFoundException(payment.getAccountId());
            }
            paymentRepository.save(payment);

            account.addPayment(payment);
            accountRepository.update(account);
        } else {
            log.info("Payment {} already exists", payment.getPaymentId());
        }
    }
}
