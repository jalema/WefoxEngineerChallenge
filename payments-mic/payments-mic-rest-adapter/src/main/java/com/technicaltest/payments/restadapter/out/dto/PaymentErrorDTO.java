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
    String payment_id;
    String error_type;
    String error_description;
}
