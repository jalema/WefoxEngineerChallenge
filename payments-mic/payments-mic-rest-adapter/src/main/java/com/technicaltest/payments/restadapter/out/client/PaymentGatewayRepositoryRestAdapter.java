package com.technicaltest.payments.restadapter.out.client;

import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import com.technicaltest.payments.application.repositories.PaymentGatewayRepository;
import com.technicaltest.payments.domain.entities.Payment;
import com.technicaltest.payments.restadapter.out.dto.PaymentValidationDTO;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class PaymentGatewayRepositoryRestAdapter implements PaymentGatewayRepository {

    @NonNull
    private final WebClient webClient;

    @Override
    public boolean validPayment(Payment payment) {
        return webClient.post()
            .uri("/payment")
            .body(Mono.just(PaymentValidationDTO.builder()
                .payment_id(payment.getPaymentId().getId().toString())
                .account_id(payment.getAccountId().getId())
                .payment_type(payment.getPaymentType().toString())
                .credit_card(payment.getCreditCard())
                .amount(payment.getAmount())
                .build()), PaymentValidationDTO.class)
            .exchangeToMono(clientResponse -> {
                if(clientResponse.statusCode().is2xxSuccessful()) {
                    return Mono.just(Boolean.TRUE);
                }
                return Mono.just(Boolean.FALSE);
            })
            .onErrorResume(error -> Mono.just(Boolean.FALSE))
            .block();
    }
}
