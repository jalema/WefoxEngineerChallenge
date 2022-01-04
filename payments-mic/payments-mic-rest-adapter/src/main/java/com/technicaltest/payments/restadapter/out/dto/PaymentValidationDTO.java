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
    String payment_id;
    Integer account_id;
    String payment_type;
    String credit_card;
    BigDecimal amount;
}
