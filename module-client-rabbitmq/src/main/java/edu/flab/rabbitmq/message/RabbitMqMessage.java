package edu.flab.rabbitmq.message;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RabbitMqMessage<T> {

	@NotNull
	private T object;

	@NotNull
	private String queueName;

	public T getObject() {
		return object;
	}

	public String getQueueName() {
		return queueName;
	}
}
