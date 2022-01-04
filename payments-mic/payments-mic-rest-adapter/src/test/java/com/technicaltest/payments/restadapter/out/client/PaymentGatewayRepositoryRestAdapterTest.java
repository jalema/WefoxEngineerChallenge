package com.technicaltest.payments.restadapter.out.client;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import com.technicaltest.payments.domain.entities.Payment;
import com.technicaltest.payments.domain.values.AccountId;
import com.technicaltest.payments.domain.values.PaymentId;
import io.netty.channel.ChannelOption;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import reactor.netty.http.client.HttpClient;

import static com.technicaltest.payments.domain.values.PaymentType.OFFLINE;
import static org.junit.jupiter.api.Assertions.*;

class PaymentGatewayRepositoryRestAdapterIT {

    private ClientAndServer mockServer;

    @BeforeEach
    void setUp() {
        mockServer = ClientAndServer.startClientAndServer(8899);
    }

    @AfterEach
    void tearDown() {
        mockServer.stop();
    }

    @Test
    void when_validate_payment_if_status_error_should_return_not_valid() {
        // GIVEN
        var webClient = createWebClient();
        var paymentCreatedOn = LocalDateTime.of(2022, 1, 4, 17, 0, 10);
        PaymentId paymentId = new PaymentId(UUID.randomUUID());
        AccountId accountId = new AccountId(1);
        Payment payment = Payment.builder()
            .paymentId(paymentId)
            .accountId(accountId)
            .paymentType(OFFLINE)
            .amount(BigDecimal.ONE)
            .createdOn(paymentCreatedOn)
            .build();
        var sut = new PaymentGatewayRepositoryRestAdapter(webClient);
        mockServer.when(HttpRequest.request()
                .withMethod("POST")
                .withPath("/payment")).
            respond(HttpResponse.response()
                .withStatusCode(500)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withDelay(TimeUnit.MILLISECONDS, 1000));

        // WHEN
        var valid = sut.validPayment(payment);

        // THEN
        assertFalse(valid);
    }

    @Test
    void when_validate_payment_if_status_ok_and_timeout_should_return_not_valid() {
        // GIVEN
        var webClient = createWebClient();
        var paymentCreatedOn = LocalDateTime.of(2022, 1, 4, 17, 0, 10);
        PaymentId paymentId = new PaymentId(UUID.randomUUID());
        AccountId accountId = new AccountId(1);
        Payment payment = Payment.builder()
            .paymentId(paymentId)
            .accountId(accountId)
            .paymentType(OFFLINE)
            .amount(BigDecimal.ONE)
            .createdOn(paymentCreatedOn)
            .build();
        var sut = new PaymentGatewayRepositoryRestAdapter(webClient);
        mockServer.when(HttpRequest.request()
                .withMethod("POST")
                .withPath("/payment")).
            respond(HttpResponse.response()
                .withStatusCode(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withDelay(TimeUnit.MILLISECONDS, 5000));

        // WHEN
        var valid = sut.validPayment(payment);

        // THEN
        assertFalse(valid);
    }

    @Test
    void when_validate_payment_if_status_ok_should_return_valid() {
        // GIVEN
        var webClient = createWebClient();
        var paymentCreatedOn = LocalDateTime.of(2022, 1, 4, 17, 0, 10);
        PaymentId paymentId = new PaymentId(UUID.randomUUID());
        AccountId accountId = new AccountId(1);
        Payment payment = Payment.builder()
            .paymentId(paymentId)
            .accountId(accountId)
            .paymentType(OFFLINE)
            .amount(BigDecimal.ONE)
            .createdOn(paymentCreatedOn)
            .build();
        var sut = new PaymentGatewayRepositoryRestAdapter(webClient);
        mockServer.when(HttpRequest.request()
                .withMethod("POST")
                .withPath("/payment")).
            respond(HttpResponse.response()
                .withStatusCode(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withDelay(TimeUnit.MILLISECONDS, 1000));

        // WHEN
        var valid = sut.validPayment(payment);

        // THEN
        assertTrue(valid);
    }

    private WebClient createWebClient() {
        HttpClient httpClient = HttpClient.create()
            .responseTimeout(Duration.ofSeconds(3))
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
        return WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .baseUrl("http://localhost:8899")
            .build();
    }
}