package com.mw.kafka.consumer.configuration;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PrometheusConfiguration {

    @Bean
    public DistributionSummary kafkaLatencySummary(MeterRegistry meterRegistry){
        return DistributionSummary.builder("kafka_latency")
                .description("end-to-end latency")
                .baseUnit("ms")
                .publishPercentiles(0.5, 0.9, 0.99)
                .register(meterRegistry);
    }

    @Bean
    public Counter opsCounter(MeterRegistry meterRegistry) {
        return Counter.builder("send_attempts_total")
                .description("Total number of send operations")
                .register(meterRegistry);
    }

    @Bean
    public Counter errorCounter(MeterRegistry meterRegistry) {
        return Counter.builder("send_errors_total")
                .description("Total number of errors")
                .register(meterRegistry);
    }
}
