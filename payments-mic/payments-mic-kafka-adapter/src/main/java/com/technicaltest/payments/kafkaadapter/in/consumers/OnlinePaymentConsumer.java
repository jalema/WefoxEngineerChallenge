package com.technicaltest.payments.kafkaadapter.in.consumers;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import com.technicaltest.payments.application.usecases.RegisterNotValidatedPaymentUseCase;
import com.technicaltest.payments.domain.entities.Payment;
import com.technicaltest.payments.domain.values.AccountId;
import com.technicaltest.payments.domain.values.PaymentId;
import com.technicaltest.payments.domain.values.PaymentType;
import com.technicaltest.payments.kafkaadapter.exceptions.OnlinePaymentExpected;
import com.technicaltest.payments.kafkaadapter.in.dto.OnlinePaymentDTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component("onlinePayment")
@Slf4j
@RequiredArgsConstructor
public class OnlinePaymentConsumer implements Consumer<OnlinePaymentDTO> {

    @NonNull
    private final RegisterNotValidatedPaymentUseCase useCase;

    @NonNull
    private final Clock clock;

    @Override
    public void accept(OnlinePaymentDTO onlinePaymentDTO) {
        if(PaymentType.ONLINE != PaymentType.of(onlinePaymentDTO.getPayment_type())) {
            throw new OnlinePaymentExpected(onlinePaymentDTO.getPayment_id(), onlinePaymentDTO.getPayment_type());
        }
        useCase.validateAndSavePayment(Payment.builder()
            .paymentId(new PaymentId(UUID.fromString(onlinePaymentDTO.getPayment_id())))
            .accountId(new AccountId(onlinePaymentDTO.getAccount_id()))
            .paymentType(PaymentType.ONLINE)
            .amount(onlinePaymentDTO.getAmount())
            .creditCard(onlinePaymentDTO.getCredit_card())
            .createdOn(LocalDateTime.now(clock))
            .build());
    }
}
