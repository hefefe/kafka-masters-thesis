package com.mw.master.kafka.configuration;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaAdminConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServersUrl;

    @Value("${kafka-test-app.kafka.topics.name}")
    private String topic;

    @Value("${kafka-test-app.kafka.topics.partitions}")
    private int topicPartitions;

    @Value("${kafka-test-app.kafka.topics.replicationFactor}")
    private short topicReplicationFactor;

    @Bean
    public KafkaAdmin kafkaAdmin(){
        return new KafkaAdmin(Map.of(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServersUrl));
    }

    @Bean
    public NewTopic mailTopic(){
        return new NewTopic(topic, topicPartitions, topicReplicationFactor);
    }
}