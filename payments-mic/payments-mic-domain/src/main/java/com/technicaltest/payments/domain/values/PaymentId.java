package com.technicaltest.payments.domain.values;

import java.util.UUID;

import lombok.NonNull;
import lombok.Value;

@Value
public class PaymentId {
    @NonNull
    UUID id;
}
