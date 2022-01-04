package com.technicaltest.payments.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {
	"com.technicaltest.payments.boot",
	"com.technicaltest.payments.kafkaadapter.in.consumers",
	"com.technicaltest.payments.application.services",
	"com.technicaltest.payments.jpaadapter.repositories",
	"com.technicaltest.payments.restadapter.out.client"
})
@EntityScan( basePackages = { "com.technicaltest.payments.jpaadapter.entities" })
public class PaymentsMicApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentsMicApplication.class, args);
	}

}
