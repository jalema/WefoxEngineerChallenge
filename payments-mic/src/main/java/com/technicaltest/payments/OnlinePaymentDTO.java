package com.technicaltest.payments;

import lombok.Data;

@Data
public class OnlinePaymentDTO {
    String payment_id;
    String account_id;
    String payment_type;
    String credit_card;
    Integer amount;
    Integer delay;
}
