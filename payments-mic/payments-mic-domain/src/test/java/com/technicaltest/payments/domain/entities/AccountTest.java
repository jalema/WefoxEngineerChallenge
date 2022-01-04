package com.technicaltest.payments.domain.entities;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import com.technicaltest.payments.domain.values.AccountId;
import com.technicaltest.payments.domain.values.PaymentId;
import org.junit.jupiter.api.Test;

import static com.technicaltest.payments.domain.values.PaymentType.OFFLINE;
import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void when_add_payment_to_account_should_update_last_payment_date_of_account() {
        // GIVEN
        var paymentCreatedOn = LocalDateTime.of(2022, 1, 4, 17, 0, 10);
        var paymentId = new PaymentId(UUID.randomUUID());
        var accountId = new AccountId(1);
        var payment = Payment.builder()
            .paymentId(paymentId)
            .accountId(accountId)
            .paymentType(OFFLINE)
            .amount(BigDecimal.ONE)
            .createdOn(paymentCreatedOn)
            .build();
        var sut = Account.builder()
            .accountId(accountId)
            .name("account_name")
            .email("mail@sample.com")
            .birthdate(LocalDate.of(2022, 1, 4))
            .build();

        // WHEN
        sut.addPayment(payment);

        // THEN
        assertEquals(paymentCreatedOn, sut.getLastPaymentDate());
    }
}