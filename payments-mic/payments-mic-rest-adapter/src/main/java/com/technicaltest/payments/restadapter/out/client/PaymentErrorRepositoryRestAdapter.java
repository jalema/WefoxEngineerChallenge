package com.technicaltest.payments.restadapter.out.client;

import java.time.Duration;

import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import com.technicaltest.payments.application.repositories.PaymentErrorsRepository;
import com.technicaltest.payments.domain.entities.PaymentError;
import com.technicaltest.payments.domain.exceptions.LogErrorException;
import com.technicaltest.payments.restadapter.out.dto.PaymentErrorDTO;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Repository
@AllArgsConstructor
@Slf4j
public class PaymentErrorRepositoryRestAdapter implements PaymentErrorsRepository {

    @NonNull
    private final WebClient webClient;

    @Override
    public void save(@NonNull PaymentError error) {
        webClient.post()
            .uri("/log")
            .body(Mono.just(PaymentErrorDTO.builder()
                .payment_id(error.getPaymentId().getId().toString())
                .error_type(error.getError().toString())
                .error_description(error.getDescription())
                .build()), PaymentErrorDTO.class)
            .retrieve()
            .bodyToMono(Void.class)
            .retryWhen(Retry.backoff(3, Duration.ofMillis(200)))
            .onErrorMap(ex -> new LogErrorException(error.getPaymentId(), ex))
            .block();
    }
}
