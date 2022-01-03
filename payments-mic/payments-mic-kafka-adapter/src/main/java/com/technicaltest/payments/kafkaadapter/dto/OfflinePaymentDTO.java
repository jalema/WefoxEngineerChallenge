package com.technicaltest.payments.kafkaadapter.dto;

import lombok.Data;

@Data
public class OfflinePaymentDTO {
    String payment_id;
    String account_id;
    String payment_type;
    Integer amount;
    Integer delay;
}
