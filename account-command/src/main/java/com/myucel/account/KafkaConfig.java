package com.myucel.account;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("kafka")
// Disable Spring's Kafka auto configuration. Spring and Axon auto-configs both try to create same beans, and they overlap with each-other.  
@EnableAutoConfiguration(exclude = KafkaAutoConfiguration.class)
public class KafkaConfig {

}
