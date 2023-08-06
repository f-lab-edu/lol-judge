package edu.flab.rabbitmq.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@EnableRetry
@Configuration
public class RabbitMqConfiguration {

	@Value("${spring.rabbitmq.host}")
	private String host;

	@Value("${spring.rabbitmq.stream.username}")
	private String username;

	@Value("${spring.rabbitmq.stream.password}")
	private String password;

	@Value("${spring.rabbitmq.port}")
	private int port;

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory cf = new CachingConnectionFactory(host, port);
		cf.setUsername(username);
		cf.setPassword(password);
		return cf;
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(messageConverter);
		return rabbitTemplate;
	}

	@Bean
	public MessageConverter messageConverter() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		return new Jackson2JsonMessageConverter(objectMapper);
	}
}
