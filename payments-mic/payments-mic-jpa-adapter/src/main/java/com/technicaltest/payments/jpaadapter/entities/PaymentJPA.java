package com.technicaltest.payments.jpaadapter.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentJPA {

    @Id
    @Column(name = "payment_id", length = 100)
    String paymentId;

    @Column(name = "account_id", nullable = false)
    Integer accountId;

    @Column(name = "payment_type", nullable = false, length = 150)
    String paymentType;

    @Column(name = "credit_card", length = 100)
    String creditCard;

    @Column(name = "amount", nullable = false)
    BigDecimal amount;

    @Column(name = "created_on")
    LocalDateTime createdOn;
}
