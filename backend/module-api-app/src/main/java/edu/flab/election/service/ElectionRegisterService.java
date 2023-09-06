package edu.flab.election.service;

import java.time.Duration;
import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.flab.election.domain.Candidate;
import edu.flab.election.domain.Election;
import edu.flab.election.domain.ElectionStatus;
import edu.flab.election.dto.ElectionRegisterRequestDto;
import edu.flab.election.dto.ElectionRegisterResponseDto;
import edu.flab.election.repository.CandidateMapper;
import edu.flab.election.repository.ElectionMapper;
import edu.flab.member.domain.Member;
import edu.flab.member.service.MemberFindService;
import edu.flab.rabbitmq.config.RabbitMqQueueName;
import edu.flab.rabbitmq.domain.RabbitMqSender;
import edu.flab.rabbitmq.message.RabbitMqMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ElectionRegisterService {
	private final MemberFindService memberFindService;
	private final CandidateMapper candidateMapper;
	private final ElectionMapper electionMapper;
	private final RabbitMqSender rabbitMqSender;

	@Transactional
	public ElectionRegisterResponseDto register(String hostEmail, ElectionRegisterRequestDto dto) {
		Election election = saveElection(dto);
		Candidate host = addCandidateToElection(election, hostEmail, dto.getChampion(), dto.getOpinion());
		Candidate participant = addCandidateToElection(election, dto.getParticipantEmail());

		rabbitMqSender.send(new RabbitMqMessage<>(election.getId(), RabbitMqQueueName.ELECTION_REGISTER));

		log.info("재판이 생성되었습니다. <electionId={}><hostEmail={}><participantEmail={}>", election.getId(), hostEmail, dto.getParticipantEmail());

		return ElectionRegisterResponseDto.builder()
			.electionId(election.getId())
			.hostId(host.getId())
			.participantId(participant.getId())
			.build();
	}

	private Election saveElection(ElectionRegisterRequestDto dto) {
		Election election = Election.builder()
			.status(ElectionStatus.PENDING)
			.youtubeUrl(dto.getYoutubeUrl())
			.cost(dto.getCost())
			.createdAt(OffsetDateTime.now())
			.endedAt(OffsetDateTime.now().plus(Duration.ofMinutes(dto.getProgressTime())))
			.build();

		electionMapper.save(election);
		return election;
	}

	private Candidate addCandidateToElection(Election election, String email, String champion, String opinion) {
		Member member = memberFindService.findActiveMember(email);
		Candidate candidate = new Candidate(member.getId(), election.getId(), opinion, champion);
		election.addCandidate(candidate);
		candidateMapper.save(candidate);
		return candidate;
	}

	private Candidate addCandidateToElection(Election election, String email) {
		return addCandidateToElection(election, email, "", "");
	}
}

