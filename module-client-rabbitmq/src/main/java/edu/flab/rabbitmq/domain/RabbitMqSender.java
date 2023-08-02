package edu.flab.rabbitmq.domain;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import edu.flab.rabbitmq.message.RabbitMqMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMqSender {
	private final ObjectMapper objectMapper;
	private final ConnectionFactory connectionFactory;

	@Retryable(retryFor = Exception.class)
	public <T> void send(RabbitMqMessage<T> message) {
		try (Connection connection = connectionFactory.newConnection(); Channel channel = connection.createChannel()) {
			String queueName = message.getQueueName();
			channel.queueDeclare(queueName, true, false, false, null);
			channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN,
				objectMapper.writeValueAsBytes(message.getObject()));
		} catch (IOException | TimeoutException e) {
			throw new RuntimeException(e);
		}
	}

	// 재시도에 실패했을 때, 수행되는 로직
	@Recover
	private <T> void onError(Exception exception, RabbitMqMessage<T> message) {
		log.error("RabbitMq 메시지 전송에 실패하였습니다. <메시지 = {}>", message, exception);
	}
}
