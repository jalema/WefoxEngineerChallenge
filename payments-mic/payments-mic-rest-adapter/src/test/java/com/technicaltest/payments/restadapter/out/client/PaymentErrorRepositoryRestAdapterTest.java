package com.technicaltest.payments.restadapter.out.client;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import com.technicaltest.payments.domain.entities.PaymentError;
import com.technicaltest.payments.domain.exceptions.LogErrorException;
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

import static com.technicaltest.payments.domain.values.ErrorType.NETWORK;
import static org.junit.jupiter.api.Assertions.*;

class PaymentErrorRepositoryRestAdapterIT {

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
    void when_log_if_service_status_error_should_throw_exception() {
        // GIVEN
        var webClient = createWebClient();
        var sut = new PaymentErrorRepositoryRestAdapter(webClient);

        var paymentId = new PaymentId(UUID.randomUUID());
        var paymentError = PaymentError.builder()
            .paymentId(paymentId)
            .error(NETWORK)
            .description("Some text")
            .build();
        mockServer.when(HttpRequest.request()
                .withMethod("POST")
                .withPath("/log")
                .withBody(resquestBody(paymentError))).
            respond(HttpResponse.response()
                .withStatusCode(504)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withDelay(TimeUnit.MILLISECONDS, 1000));

        // WHEN
        assertThrows(LogErrorException.class, () -> sut.save(paymentError));
    }

    @Test
    void when_log_if_service_takes_more_than_timeout_should_throw_exception() {
        // GIVEN
        var webClient = createWebClient();
        var sut = new PaymentErrorRepositoryRestAdapter(webClient);

        var paymentId = new PaymentId(UUID.randomUUID());
        var paymentError = PaymentError.builder()
            .paymentId(paymentId)
            .error(NETWORK)
            .description("Some text")
            .build();
        mockServer.when(HttpRequest.request()
                .withMethod("POST")
                .withPath("/log")
                .withBody(resquestBody(paymentError))).
            respond(HttpResponse.response()
                .withStatusCode(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withDelay(TimeUnit.MILLISECONDS, 5000));

        // WHEN
        assertThrows(LogErrorException.class, () -> sut.save(paymentError));
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

    private String resquestBody(PaymentError error) {
        return String.format("{ \"payment_id\": \"%s\", \"error_type\": \"%s\", "
                + "\"error_description\": \"%s\"}", error.getPaymentId().getId().toString(),
            error.getError().toString(), error.getDescription());
    }
}