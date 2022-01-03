package com.technicaltest.payments.kafkaadapter.in.consumers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.technicaltest.payments.kafkaadapter.in.dto.OnlinePaymentDTO;
import lombok.extern.slf4j.Slf4j;

@Component("onlinePayment")
@Slf4j
public class OnlinePaymentConsumer implements Consumer<Message<OnlinePaymentDTO>> {

    private static Map<UUID, String> messageIdToPaymentId = new HashMap<>();
    private static Map<String, OnlinePaymentDTO> paymentIdToPaymentDTO = new HashMap<>();
    private static Set<UUID> duplicatedMessageIds = new HashSet<>();
    private static Set<String> duplicatedPaymentIds = new HashSet<>();

    @Override
    public void accept(Message<OnlinePaymentDTO> onlinePaymentDTO) {
        log.debug("Online payment: {}, Headers: {}", onlinePaymentDTO.getPayload(), onlinePaymentDTO.getHeaders());
        UUID messageId = onlinePaymentDTO.getHeaders().getId();
        OnlinePaymentDTO payload = onlinePaymentDTO.getPayload();
        if(messageIdToPaymentId.containsKey(messageId)) {
            duplicatedMessageIds.add(messageId);
        } else {
            messageIdToPaymentId.put(messageId, payload.getPayment_id());
        }

        if(paymentIdToPaymentDTO.containsKey(payload.getPayment_id())){
            duplicatedPaymentIds.add(payload.getPayment_id());
        } else {
            paymentIdToPaymentDTO.put(onlinePaymentDTO.getPayload().getPayment_id(), onlinePaymentDTO.getPayload());
        }

    }
}
