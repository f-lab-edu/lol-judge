package edu.flab.election.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;

import edu.flab.election.domain.Candidate;
import edu.flab.election.domain.Election;
import edu.flab.election.domain.ElectionStatus;
import edu.flab.election.dto.CandidateUpdateOpinionRequestDto;
import edu.flab.election.dto.ElectionContentsUpdateRequestDto;
import edu.flab.election.dto.ElectionEditRequestDto;
import edu.flab.election.dto.ElectionEndTimeUpdateRequestDto;
import edu.flab.election.dto.ElectionStatusUpdateRequestDto;
import edu.flab.election.repository.CandidateMapper;
import edu.flab.election.repository.ElectionMapper;
import edu.flab.log.ExceptionLogTrace;
import edu.flab.rabbitmq.config.RabbitMqQueueName;
import edu.flab.rabbitmq.domain.RabbitMqSender;
import edu.flab.rabbitmq.message.RabbitMqMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ElectionEditService {
	private final ElectionFindService electionFindService;
	private final ElectionMapper electionMapper;
	private final RabbitMqSender rabbitMqSender;
	private final CandidateMapper candidateMapper;

	@ExceptionLogTrace
	public void update(ElectionEditRequestDto dto) {
		Election election = electionFindService.findElection(dto.getId());
		updateElection(election, dto.getYoutubeUrl(), dto.getCost());
		updateCandidate(election.getHost(), dto.getHostOpinion(), dto.getHostChampion());
		updateCandidate(election.getParticipant(), dto.getParticipantOpinion(), dto.getParticipantChampion());
		rabbitMqSender.send(new RabbitMqMessage<>(dto.getId(), RabbitMqQueueName.ELECTION_IN_PROGRESS));
		log.info("재판 내용이 수정되었습니다. <업데이트 내용={}>", dto);
	}

	private void updateElection(Election election, String youtubeUrl, int cost) {
		electionMapper.updateContentsById(
			new ElectionContentsUpdateRequestDto(election.getId(), youtubeUrl, cost));
		electionMapper.updateEndTimeById(new ElectionEndTimeUpdateRequestDto(election.getId(),
			OffsetDateTime.now().plusHours(election.getProgressTime())));
		electionMapper.updateStatusById(new ElectionStatusUpdateRequestDto(election.getId(), ElectionStatus.IN_PROGRESS));
	}

	private void updateCandidate(Candidate candidate, String opinion, String champion) {
		candidateMapper.updateOpinionById(new CandidateUpdateOpinionRequestDto(candidate.getId(), champion, opinion));
	}
}
