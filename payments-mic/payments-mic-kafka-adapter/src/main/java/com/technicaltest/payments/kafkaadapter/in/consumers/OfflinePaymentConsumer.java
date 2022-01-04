package com.technicaltest.payments.kafkaadapter.in.consumers;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import com.technicaltest.payments.application.usecases.RegisterAlreadyValidatedPaymentUseCase;
import com.technicaltest.payments.domain.entities.Payment;
import com.technicaltest.payments.domain.values.AccountId;
import com.technicaltest.payments.domain.values.PaymentId;
import com.technicaltest.payments.domain.values.PaymentType;
import com.technicaltest.payments.kafkaadapter.exceptions.OfflinePaymentExpected;
import com.technicaltest.payments.kafkaadapter.in.dto.OfflinePaymentDTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component("offlinePayment")
@Slf4j
@RequiredArgsConstructor
public class OfflinePaymentConsumer implements Consumer<OfflinePaymentDTO> {

    @NonNull
    private final RegisterAlreadyValidatedPaymentUseCase useCase;

    @NonNull
    private final Clock clock;

    @Override
    public void accept(OfflinePaymentDTO offlinePaymentDTO) {
        if(PaymentType.OFFLINE != PaymentType.of(offlinePaymentDTO.getPayment_type())) {
            throw new OfflinePaymentExpected(offlinePaymentDTO.getPayment_id(), offlinePaymentDTO.getPayment_type());
        }
        useCase.saveValidPayment(Payment.builder()
            .paymentId(new PaymentId(UUID.fromString(offlinePaymentDTO.getPayment_id())))
            .accountId(new AccountId(offlinePaymentDTO.getAccount_id()))
            .paymentType(PaymentType.OFFLINE)
            .amount(offlinePaymentDTO.getAmount())
            .createdOn(LocalDateTime.now(clock))
            .build());
    }
}
