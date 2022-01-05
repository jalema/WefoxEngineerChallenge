package com.technicaltest.payments.kafkaadapter.in.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class OfflinePaymentDTO {
    @NotNull
    private String payment_id;
    @NotNull
    private Integer account_id;
    @NotNull
    private String payment_type;
    @NotNull
    private BigDecimal amount;
    private Integer delay;
}
