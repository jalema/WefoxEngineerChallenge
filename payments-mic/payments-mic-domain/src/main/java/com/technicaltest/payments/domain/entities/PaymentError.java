package com.technicaltest.payments.domain.entities;

import com.technicaltest.payments.domain.values.ErrorType;
import com.technicaltest.payments.domain.values.PaymentId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Builder
@ToString
@Getter
public class PaymentError {
    @NonNull PaymentId paymentId;
    @NonNull ErrorType error;
    @NonNull String description;
}
