package edu.flab.rabbitmq.message;

public interface RabbitMqMessage<T> {
	T getObject();

	String getQueueName();
}
