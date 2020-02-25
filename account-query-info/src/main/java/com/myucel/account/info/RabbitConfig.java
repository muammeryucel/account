package com.myucel.account.info;

import java.util.Collections;

import org.axonframework.extensions.amqp.eventhandling.AMQPMessageConverter;
import org.axonframework.extensions.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.springboot.autoconfig.AxonServerAutoConfiguration;
import org.springframework.amqp.core.AbstractExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Binding.DestinationType;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.rabbitmq.client.Channel;

@EnableRabbit
@Configuration
@Profile("rabbit")
@EnableAutoConfiguration(exclude = { AxonServerAutoConfiguration.class })
public class RabbitConfig {

	private static final String X_DEAD_LETTER_EXCHANGE = "x-dead-letter-exchange";
	private static final String CONSISTENT_HASH_SUFFIX = ".consistent.hash";
	private static final String CONSISTENT_HASH_QUEUE_TYPE = "x-consistent-hash";

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
	public Exchange consistentHashExchange(@Value("${application.exchange}" + CONSISTENT_HASH_SUFFIX) String name) {
		AbstractExchange exchange = new AbstractExchange(name) {

			@Override
			public String getType() {
				return CONSISTENT_HASH_QUEUE_TYPE;
			}
		};
		exchange.setInternal(true);
		return exchange;
	}

	@Bean
	public Binding consistentHashBinding(FanoutExchange exchange, Exchange consistentHashExchange) {
		return BindingBuilder.bind(consistentHashExchange).to(exchange);
	}

	@Bean
	public Queue queue(@Value("${application.queue}") String name,
			@Value("${application.dead-letter.exchange}") String deadLetterExchange) {
		Queue queue = new Queue(name);
		queue.addArgument(X_DEAD_LETTER_EXCHANGE, deadLetterExchange);
		return queue;
	}

	@Bean
	public FanoutExchange deadLetterExchange(@Value("${application.dead-letter.exchange}") String name) {
		return new FanoutExchange(name);
	}

	@Bean
	public Queue deadLetterQueue(@Value("${application.dead-letter.queue}") String name) {
		return new Queue(name);
	}

	@Bean
	public Binding bindingDeadLetter(FanoutExchange deadLetterExchange, Queue deadLetterQueue) {
		return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange);
	}

	@Bean
	public Binding binding(@Value("${application.exchange}") String exchange,
			@Value("${application.queue}") String queue) {
		return new Binding(queue, DestinationType.QUEUE, exchange + CONSISTENT_HASH_SUFFIX, "1",
				Collections.emptyMap());
	}

//	@Bean
//	public MessageRecoverer testMessageRecoverer() {
//		return (message, cause) -> {
//			throw new AmqpRejectAndDontRequeueException(cause);
//		};
//	}

}