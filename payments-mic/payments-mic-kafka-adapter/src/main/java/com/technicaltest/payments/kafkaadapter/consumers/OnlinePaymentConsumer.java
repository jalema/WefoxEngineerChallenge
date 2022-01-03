package com.technicaltest.payments.kafkaadapter.consumers;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import com.technicaltest.payments.kafkaadapter.dto.OnlinePaymentDTO;
import lombok.extern.slf4j.Slf4j;

@Component("onlinePayment")
@Slf4j
public class OnlinePaymentConsumer implements Consumer<OnlinePaymentDTO> {

    @Override
    public void accept(OnlinePaymentDTO onlinePaymentDTO) {
        log.error("Online payment: {}", onlinePaymentDTO);
    }
}
