package edu.flab.rabbitmq.domain;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import edu.flab.rabbitmq.message.RabbitMqMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RabbitMqSender {
	private final ConnectionFactory connectionFactory;

	public RabbitMqSender(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	@Retryable(retryFor = Exception.class)
	public void notify(RabbitMqMessage message) {
		try (Connection connection = connectionFactory.newConnection(); Channel channel = connection.createChannel()) {
			channel.queueDeclare(message.getQueueName(), true, false, false, null);
			channel.basicPublish("", message.getQueueName(), MessageProperties.PERSISTENT_TEXT_PLAIN,
				message.toString().getBytes());
		} catch (IOException | TimeoutException e) {
			throw new RuntimeException(e);
		}
	}

	// 재시도에 실패했을 때, 수행되는 로직
	@Recover
	private void onError(Exception exception, RabbitMqMessage message) {
		log.error("RabbitMq 메시지 전송에 실패하였습니다. <메시지 = {}>", message, exception);
	}
}
