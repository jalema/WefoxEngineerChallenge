package com.technicaltest.payments.domain.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.technicaltest.payments.domain.values.AccountId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Builder
@ToString
@Getter
public class Account {
    @NonNull AccountId accountId;
    String name;
    @NonNull String email;
    LocalDate birthdate;
    LocalDateTime lastPaymentDate;

    public void addPayment(Payment payment) {
        this.lastPaymentDate = payment.getCreatedOn();
    }
}
