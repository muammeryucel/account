package com.myucel.account;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
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
		FanoutExchange exchange = new FanoutExchange(name);
		return exchange;
	}
	
	@Bean
	public Queue queue() {
		Queue queue = new Queue("account.command.test");
		return queue;
	}

	@Bean
	public Binding binding(FanoutExchange exchange, Queue queue) {
		return BindingBuilder.bind(queue).to(exchange);
	}
}