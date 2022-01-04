package com.technicaltest.payments.application.services;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.technicaltest.payments.application.repositories.AccountRepository;
import com.technicaltest.payments.application.repositories.PaymentErrorsRepository;
import com.technicaltest.payments.application.repositories.PaymentGatewayRepository;
import com.technicaltest.payments.application.repositories.PaymentRepository;
import com.technicaltest.payments.application.usecases.RegisterAlreadyValidatedPaymentUseCase;
import com.technicaltest.payments.application.usecases.RegisterNotValidatedPaymentUseCase;
import com.technicaltest.payments.domain.entities.Account;
import com.technicaltest.payments.domain.entities.Payment;
import com.technicaltest.payments.domain.entities.PaymentError;
import com.technicaltest.payments.domain.exceptions.PaymentAccountNotFoundException;
import com.technicaltest.payments.domain.exceptions.InvalidPaymentException;
import com.technicaltest.payments.domain.exceptions.PaymentException;
import com.technicaltest.payments.domain.values.ErrorType;
import com.technicaltest.payments.domain.values.PaymentId;
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
    @NonNull
    private final PaymentErrorsRepository paymentErrorsRepository;

    @Transactional
    @Override
    public void saveValidPayment(Payment payment) {
        logOnError(payment.getPaymentId(), () -> {
            if (!paymentRepository.exists(payment.getPaymentId())) {
                savePayment(payment);
            } else {
                log.info("Payment {} already exists", payment.getPaymentId());
            }
        });
    }

    @Transactional
    @Override
    public void validateAndSavePayment(Payment payment) {
        logOnError(payment.getPaymentId(), () -> {
            if(!paymentRepository.exists(payment.getPaymentId())) {
                saveIfValidPayment(payment);
            } else {
                log.info("Payment {} already exists", payment.getPaymentId());
            }
        });
    }

    private void savePayment(Payment payment) {
        Account account = accountRepository.retrieveAndLock(payment.getAccountId());
        if (Objects.isNull(account)) {
            throw new PaymentAccountNotFoundException(payment.getPaymentId(), payment.getAccountId());
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

    private void logOnError(PaymentId paymentId, Runnable action) {
        try {
            action.run();
        } catch (PaymentException pe) {
            paymentErrorsRepository.save(pe.toError());
        } catch (Exception ex) {
            paymentErrorsRepository.save(PaymentError.builder()
                .paymentId(paymentId)
                .error(ErrorType.OTHER)
                .description(ex.getMessage())
                .build());
        }
    }
}
