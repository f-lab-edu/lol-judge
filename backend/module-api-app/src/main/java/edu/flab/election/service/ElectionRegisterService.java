package edu.flab.election.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.flab.election.domain.Candidate;
import edu.flab.election.domain.CandidateStatus;
import edu.flab.election.domain.Election;
import edu.flab.election.domain.ElectionStatus;
import edu.flab.election.dto.ElectionRegisterRequestDto;
import edu.flab.election.dto.ElectionRegisterResponseDto;
import edu.flab.election.repository.ElectionJpaRepository;
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
	private final RabbitMqSender rabbitMqSender;
	private final ElectionJpaRepository electionJpaRepository;

	@Transactional
	public ElectionRegisterResponseDto register(String hostEmail, ElectionRegisterRequestDto dto) {
		Election election = Election.builder()
			.status(ElectionStatus.PENDING)
			.youtubeUrl(dto.getYoutubeUrl())
			.cost(dto.getCost())
			.progressTime(dto.getProgressTime())
			.createdAt(OffsetDateTime.now())
			.build();

		Candidate host = addCandidateToElection(election, CandidateStatus.HOST, hostEmail, dto.getChampion(),
			dto.getOpinion());
		Candidate participant = addCandidateToElection(election, CandidateStatus.PARTICIPANT, dto.getParticipantEmail(),
			"", "");

		electionJpaRepository.save(election);

		rabbitMqSender.send(new RabbitMqMessage<>(election.getId(), RabbitMqQueueName.ELECTION_REGISTER));

		log.info("재판이 생성되었습니다. <electionId={}><hostEmail={}><participantEmail={}>", election.getId(), hostEmail,
			dto.getParticipantEmail());

		return ElectionRegisterResponseDto.builder()
			.electionId(election.getId())
			.hostId(host.getId())
			.participantId(participant.getId())
			.build();
	}

	private Candidate addCandidateToElection(Election election, CandidateStatus status, String email, String champion,
		String opinion) {
		Member member = memberFindService.findActiveMember(email);
		Candidate candidate = Candidate.builder()
			.opinion(opinion)
			.champion(champion)
			.candidateStatus(status)
			.build();
		candidate.setMember(member);
		candidate.setElection(election);
		return candidate;
	}
}

