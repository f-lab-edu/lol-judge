package edu.flab.election.message;

import edu.flab.election.domain.Election;
import edu.flab.rabbitmq.config.RabbitMqConstant;
import edu.flab.rabbitmq.message.RabbitMqMessage;
import edu.flab.rabbitmq.message.RabbitMqMessageType;
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
public class ElectionNotifyMessage implements RabbitMqMessage {

	@NotNull
	private RabbitMqMessageType messageType;

	@NotNull
	private Election election;

	@Override
	public RabbitMqMessageType getMessageType() {
		return messageType;
	}

	@Override
	public String getQueueName() {
		return RabbitMqConstant.NOTIFICATION_QUEUE_NAME;
	}
}
