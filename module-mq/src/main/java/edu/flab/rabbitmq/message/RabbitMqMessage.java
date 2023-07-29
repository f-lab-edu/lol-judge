package edu.flab.rabbitmq.message;

public interface RabbitMqMessage {
	RabbitMqMessageType getMessageType();

	String getQueueName();
}
