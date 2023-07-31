package edu.flab.rabbitmq.message;

public interface RabbitMqMessage<T> {
	RabbitMqMessageType getMessageType();

	T getMessageObject();

	String getQueueName();
}
