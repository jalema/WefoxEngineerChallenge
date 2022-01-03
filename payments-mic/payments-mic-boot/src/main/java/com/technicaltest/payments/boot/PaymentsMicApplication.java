package com.technicaltest.payments.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {
	"com.technicaltest.payments.boot",
	"com.technicaltest.payments.kafkaadapter.consumers"
})
public class PaymentsMicApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentsMicApplication.class, args);
	}

}
