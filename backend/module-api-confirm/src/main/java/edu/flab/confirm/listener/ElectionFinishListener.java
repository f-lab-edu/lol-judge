package edu.flab.confirm.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

import edu.flab.confirm.service.JudgePointAssignService;
import edu.flab.election.domain.Election;
import edu.flab.rabbitmq.config.RabbitMqQueueName;
import edu.flab.rabbitmq.message.RabbitMqMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ElectionFinishListener {

	private final JudgePointAssignService judgePointAssignService;

	@RabbitListener(queues = RabbitMqQueueName.ELECTION_FINISHED, ackMode = "AUTO")
	public void listenFinish(RabbitMqMessage<Election> rabbitMqMessage) {
		Election election = rabbitMqMessage.getObject();
		judgePointAssignService.assignJudgePoint(election.getId());
	}
}
