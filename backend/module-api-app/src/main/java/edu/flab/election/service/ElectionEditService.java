package edu.flab.election.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.flab.election.domain.Candidate;
import edu.flab.election.domain.CandidateStatus;
import edu.flab.election.domain.Election;
import edu.flab.election.domain.ElectionStatus;
import edu.flab.election.dto.ElectionEditRequestDto;
import edu.flab.log.ExceptionLogTrace;
import edu.flab.rabbitmq.config.RabbitMqQueueName;
import edu.flab.rabbitmq.domain.RabbitMqSender;
import edu.flab.rabbitmq.message.RabbitMqMessage;
import edu.flab.util.YoutubeThumbnailExtractor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ElectionEditService {
	private final ElectionFindService electionFindService;
	private final RabbitMqSender rabbitMqSender;

	@Transactional
	@ExceptionLogTrace
	public void edit(ElectionEditRequestDto dto) {
		Election election = electionFindService.findElection(dto.getId());
		Candidate host = election.getCandidate(CandidateStatus.HOST);
		Candidate participant = election.getCandidate(CandidateStatus.PARTICIPANT);

		election.setContents(dto.getYoutubeUrl(), dto.getCost());
		election.setStatus(ElectionStatus.IN_PROGRESS);
		election.setThumbnailUrl(YoutubeThumbnailExtractor.getThumbnailUrl(election.getYoutubeUrl()));
		host.changeContents(dto.getHostOpinion(), dto.getHostChampion());
		participant.changeContents(dto.getParticipantOpinion(), dto.getParticipantChampion());

		rabbitMqSender.send(new RabbitMqMessage<>(dto.getId(), RabbitMqQueueName.ELECTION_IN_PROGRESS));
		log.info("재판 내용이 수정되었습니다. <업데이트 내용={}>", dto);
	}
}
