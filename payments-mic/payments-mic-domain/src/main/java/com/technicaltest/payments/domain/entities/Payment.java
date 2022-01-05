package com.technicaltest.payments.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.technicaltest.payments.domain.values.AccountId;
import com.technicaltest.payments.domain.values.PaymentId;
import com.technicaltest.payments.domain.values.PaymentType;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Builder
@ToString
@Getter
public class Payment {
    @NonNull
    private PaymentId paymentId;
    @NonNull
    private AccountId accountId;
    @NonNull
    private PaymentType paymentType;
    private String creditCard;
    @NonNull
    private BigDecimal amount;
    private LocalDateTime createdOn;
}
