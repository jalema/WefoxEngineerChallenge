package com.technicaltest.payments.domain.entities;

import com.technicaltest.payments.domain.values.ErrorType;
import com.technicaltest.payments.domain.values.PaymentId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Builder
@ToString
@Getter
@EqualsAndHashCode
public class PaymentError {
    @NonNull
    private PaymentId paymentId;
    @NonNull
    private ErrorType error;
    @NonNull
    private String description;
}
