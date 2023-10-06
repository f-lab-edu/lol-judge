package edu.flab.election.schedule;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.flab.election.domain.Election;
import edu.flab.election.domain.ElectionStatus;
import edu.flab.election.repository.ElectionJpaRepository;
import edu.flab.election.service.JudgeElectionService;
import edu.flab.rabbitmq.config.RabbitMqQueueName;
import edu.flab.rabbitmq.domain.RabbitMqSender;
import edu.flab.rabbitmq.message.RabbitMqMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JudgeElectionScheduler {

	private final ElectionJpaRepository electionJpaRepository;
	private final RabbitMqSender rabbitMqSender;
	private final JudgeElectionService judgeElectionService;

	@Scheduled(cron = "0 * * * * *")
	@Transactional
	public void finishElection() {
		log.info("[스케줄러 실행] 재판의 결과를 산출합니다. (IN_PROGRESS → FINISHED)");
		List<Election> elections = electionJpaRepository.findByStatusAndEndedAt(ElectionStatus.IN_PROGRESS,
			OffsetDateTime.now());

		elections.forEach(e -> {
			judgeElectionService.judge(e);
			rabbitMqSender.send(new RabbitMqMessage<>(e.getId(), RabbitMqQueueName.ELECTION_IN_PROGRESS));
		});
	}
}
