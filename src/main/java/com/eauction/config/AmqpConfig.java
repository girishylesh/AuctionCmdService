package com.eauction.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {
	
	@Value("${axon.amqp.exchange}")
	private String exchange;
	
	@Value("${eauction.rabbitmq.queue}")
	private String queueName;

	@Bean
	public Exchange eventsExchange() {
		return ExchangeBuilder.topicExchange(exchange).build();
	}

	@Bean
	public Queue eventsQueue() {
		return QueueBuilder.durable(queueName).build();
	}

	@Bean
	public Binding eventsBinding() {
		return BindingBuilder.bind(eventsQueue()).to(eventsExchange()).with("#").noargs();
	}

}
