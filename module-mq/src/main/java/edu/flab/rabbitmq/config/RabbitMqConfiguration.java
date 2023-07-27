package edu.flab.rabbitmq.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rabbitmq.client.ConnectionFactory;

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
		ConnectionFactory cf = new ConnectionFactory();
		cf.setHost(host);
		cf.setUsername(username);
		cf.setPassword(password);
		cf.setPort(port);
		return cf;
	}
}
