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
    @NonNull
    private AccountId accountId;
    private String name;
    @NonNull
    private String email;
    private LocalDate birthdate;
    private LocalDateTime lastPaymentDate;

    public void addPayment(@NonNull Payment payment) {
        this.lastPaymentDate = payment.getCreatedOn();
    }
}
