package edu.flab.rabbitmq.domain;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import edu.flab.rabbitmq.annotation.Retry;
import edu.flab.rabbitmq.message.RabbitMqMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RabbitMqSender {
	private final ConnectionFactory connectionFactory;

	public RabbitMqSender(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	@Retry
	public void notify(RabbitMqMessage message) {
		try (Connection connection = connectionFactory.newConnection();
			 Channel channel = connection.createChannel()) {
			channel.queueDeclare(message.getQueueName(), true, false, false, null);
			channel.basicPublish("", message.getQueueName(), MessageProperties.PERSISTENT_TEXT_PLAIN,
				message.toString().getBytes());
		} catch (IOException | TimeoutException e) {
			throw new RuntimeException(e);
		}
	}
}
