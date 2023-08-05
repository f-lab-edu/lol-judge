package edu.flab.election.amqp;

import edu.flab.election.domain.Election;
import edu.flab.rabbitmq.message.RabbitMqMessage;
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
public class ElectionNotifyMessage implements RabbitMqMessage<Election> {

	@NotNull
	private Election election;

	@NotNull
	private String queueName;

	@Override
	public Election getObject() {
		return election;
	}

	@Override
	public String getQueueName() {
		return queueName;
	}
}
