package com.technicaltest.payments.restadapter.out.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentErrorDTO {
    private String payment_id;
    private String error_type;
    private String error_description;
}
