package com.myucel.account.balance;

import org.axonframework.extensions.amqp.eventhandling.AMQPMessageConverter;
import org.axonframework.extensions.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.rabbitmq.client.Channel;

@EnableRabbit
@Configuration
@Profile("rabbit")
public class RabbitConfig {

	@Bean
	public SpringAMQPMessageSource amqpMessageSource(AMQPMessageConverter messageConverter) {
		return new SpringAMQPMessageSource(messageConverter) {

			@Override
			@RabbitListener(queues = "${application.queue}")
			public void onMessage(Message message, Channel channel) {
				super.onMessage(message, channel);
			}
		};
	}

	@Bean
	public FanoutExchange exchange(@Value("${application.exchange}") String name) {
		return new FanoutExchange(name);
	}

	@Bean
	public Queue queue(@Value("${application.queue}") String name) {
		return new Queue(name);
	}

	@Bean
	public Binding binding(FanoutExchange exchange, Queue queue) {
		return BindingBuilder.bind(queue).to(exchange);
	}
}