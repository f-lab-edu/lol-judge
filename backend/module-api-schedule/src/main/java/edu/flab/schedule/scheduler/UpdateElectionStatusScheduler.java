package edu.flab.schedule.scheduler;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import edu.flab.election.domain.Election;
import edu.flab.election.domain.ElectionStatus;
import edu.flab.election.dto.ElectionStatusUpdateRequestDto;
import edu.flab.election.repository.ElectionMapper;
import edu.flab.rabbitmq.config.RabbitMqQueueName;
import edu.flab.rabbitmq.domain.RabbitMqSender;
import edu.flab.rabbitmq.message.RabbitMqMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateElectionStatusScheduler {

	private final ElectionMapper electionMapper;

	private final RabbitMqSender rabbitMqSender;

	@Scheduled(cron = "0 * * * * *")
	public void updateToInProgress() {
		log.info("[스케줄러 실행] 내용 합의가 끝난 재판의 상태를 변경합니다. (READY → IN_PROGRESS)");
		List<Election> elections = electionMapper.findAllElectionsByStatus(ElectionStatus.READY);

		elections.forEach(e -> {
			electionMapper.updateStatusById(new ElectionStatusUpdateRequestDto(e.getId(), ElectionStatus.IN_PROGRESS));
			rabbitMqSender.send(new RabbitMqMessage<>(e.getId(), RabbitMqQueueName.ELECTION_IN_PROGRESS));
		});
	}
}