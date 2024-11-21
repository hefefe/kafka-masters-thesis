package com.mw.kafka.consumer.configuration;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PrometheusConfiguration {

    @Bean
    public Counter errorCounter(MeterRegistry meterRegistry) {
        return Counter.builder("spring_kafka_operations")
                .tag("type", "error")
                .register(meterRegistry);
    }
}
