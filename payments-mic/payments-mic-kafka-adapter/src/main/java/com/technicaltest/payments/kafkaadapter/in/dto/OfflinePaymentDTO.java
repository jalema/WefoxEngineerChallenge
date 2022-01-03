package com.technicaltest.payments.kafkaadapter.in.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class OfflinePaymentDTO {
    @NotNull String payment_id;
    @NotNull Integer account_id;
    @NotNull String payment_type;
    @NotNull BigDecimal amount;
    Integer delay;
}
