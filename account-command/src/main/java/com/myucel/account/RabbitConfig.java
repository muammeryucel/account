package com.myucel.account;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@EnableRabbit
@Configuration
@Profile("rabbit")
public class RabbitConfig {

	@Bean
	public FanoutExchange exchange(@Value("${axon.amqp.exchange}") String name) {
		return new FanoutExchange(name);
	}
}