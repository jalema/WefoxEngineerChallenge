package com.technicaltest.payments.restadapter.out.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentValidationDTO {
    private String payment_id;
    private Integer account_id;
    private String payment_type;
    private String credit_card;
    private BigDecimal amount;
}
