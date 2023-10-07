package edu.flab.notify.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.flab.election.domain.Election;
import edu.flab.election.service.ElectionFindService;
import edu.flab.notify.service.NotifyService;
import edu.flab.rabbitmq.config.RabbitMqQueueName;
import edu.flab.rabbitmq.message.RabbitMqMessage;
import edu.flab.web.config.ServerAddressProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ElectionNotifyListener {

	private final ElectionFindService electionFindService;
	private final NotifyService notifyService;
	private final ServerAddressProperties frontendServer;

	@Transactional
	@RabbitListener(queues = RabbitMqQueueName.ELECTION_FINISHED, ackMode = "AUTO")
	public void listenFinish(RabbitMqMessage<Long> rabbitMqMessage) {
		Long electionId = rabbitMqMessage.getObject();

		log.info("[알림 시스템] 재판 판결에 대한 알림 처리를 시작합니다. electionId = {}", electionId);

		Election election = electionFindService.findElection(electionId);
		election.getCandidates().stream()
			.flatMap(cs -> cs.getVotes().stream())
			.forEach(v -> {
				if (v.isGroupOfWinner()) {
					notifyService.notify(v.getMember(),
						"재판에서 승리하셨습니다. 재판링크=" + getElectionDetailUrl(election.getId()));
				} else {
					notifyService.notify(v.getMember(),
						"재판에서 패배하셨습니다 재판링크=" + getElectionDetailUrl(election.getId()));
				}
			});

		log.info("[알림 시스템] 재판 판결에 대한 알림 처리 종료...!");
	}

	private String getElectionDetailUrl(long electionId) {
		return frontendServer.fullAddress() + "elections" + "/" + electionId;
	}
}
