package edu.flab.rabbitmq.domain;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.stereotype.Component;

import edu.flab.rabbitmq.message.RabbitMqMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMqSender {
	private final RabbitTemplate rabbitTemplate;

	@Retryable(retryFor = Exception.class)
	public <T> void send(RabbitMqMessage<T> message) {
		try {
			rabbitTemplate.convertAndSend(message.getQueueName(), message);
		} catch (AmqpException e) {
			log.warn("RabbitMq 메시지 전송에 실패하였습니다. <메시지 = {}>", message, e);
			throw e;
		}
	}

	// 재시도에 실패했을 때, 수행되는 로직
	@Recover
	private <T> void onError(RuntimeException exception, RabbitMqMessage<T> message) {
		int retryCount = RetrySynchronizationManager.getContext().getRetryCount();
		log.error("RabbitMq 메시지 전송 재시도를 시도하였으나 실패하였습니다. <메시지 = {}> <재시도 횟수 = {}>", message, retryCount, exception);
		throw exception;
	}
}
