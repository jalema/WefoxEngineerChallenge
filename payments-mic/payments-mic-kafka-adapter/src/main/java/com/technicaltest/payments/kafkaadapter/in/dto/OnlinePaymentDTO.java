package com.technicaltest.payments.kafkaadapter.in.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class OnlinePaymentDTO {
    @NotNull String payment_id;
    @NotNull Integer account_id;
    @NotNull String payment_type;
    @NotNull String credit_card;
    @NotNull BigDecimal amount;
    Integer delay;
}
