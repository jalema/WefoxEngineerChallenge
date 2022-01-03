package com.technicaltest.payments;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component("offlinePayment")
@Slf4j
public class OfflinePaymentConsumer implements Consumer<OfflinePaymentDTO> {

    @Override
    public void accept(OfflinePaymentDTO offlinePaymentDTO) {
        if(offlinePaymentDTO.getAmount() < 10){
            throw new PaymentAmountAtLeastTenException(offlinePaymentDTO.getPayment_id(), offlinePaymentDTO.getAmount());
        } else {
            log.error("Offline payment: {}", offlinePaymentDTO);
        }
    }
}
