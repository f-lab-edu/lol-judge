package edu.flab.notify.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

import edu.flab.election.domain.Election;
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
	public void listenRegistration(RabbitMqMessage<Election> message) {
		Election Election = message.getObject();
		String notification = "재판이 생성되었습니다. 자신의 의견을 작성하세요. 링크 = " + apiUrlUtil.getElectionApiUrl(Election);
		electionNotificationService.notifyToCandidates(Election, notification);
	}
}
