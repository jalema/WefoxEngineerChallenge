package com.technicaltest.payments.boot;

import java.time.Clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public Clock defaultClock() {
        return Clock.systemUTC();
    }
}
