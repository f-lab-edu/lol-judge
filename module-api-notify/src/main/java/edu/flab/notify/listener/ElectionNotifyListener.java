package edu.flab.notify.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

import edu.flab.notify.service.ElectionNotificationService;
import edu.flab.notify.util.ApiUrlUtil;
import edu.flab.rabbitmq.config.RabbitMqQueueName;
import edu.flab.rabbitmq.message.RabbitMqMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ElectionNotifyListener {

	private final ElectionNotificationService electionNotificationService;
	private final ApiUrlUtil apiUrlUtil;

	@RabbitListener(queues = RabbitMqQueueName.ELECTION_REGISTER, ackMode = "AUTO")
	public void listenRegistration(RabbitMqMessage<Long> message) {
		Long electionId = message.getObject();
		String notification = "재판이 생성되었습니다. 자신의 의견을 작성하세요. 링크 = " + apiUrlUtil.getElectionApiUrl(electionId);
		electionNotificationService.notifyToCandidates(electionId, notification);
	}

	@RabbitListener(queues = RabbitMqQueueName.ELECTION_IN_PROGRESS, ackMode = "AUTO")
	public void listenInProgress(RabbitMqMessage<Long> message) {
		Long electionId = message.getObject();
		String notification = "재판이 시작되었습니다. 링크 = " + apiUrlUtil.getElectionApiUrl(electionId);
		electionNotificationService.notifyToCandidates(electionId, notification);
	}

	@RabbitListener(queues = RabbitMqQueueName.ELECTION_FINISHED, ackMode = "AUTO")
	public void listenFinish(RabbitMqMessage<Long> rabbitMqMessage) {
		Long electionId = rabbitMqMessage.getObject();
		String notification = "재판이 종료되었습니다. 링크 = " + apiUrlUtil.getElectionApiUrl(electionId);
		electionNotificationService.notifyToCandidates(electionId, notification);
	}
}
