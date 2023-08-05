package edu.flab.election.service;

import java.time.Duration;
import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.flab.election.amqp.ElectionNotifyMessage;
import edu.flab.election.domain.Candidate;
import edu.flab.election.domain.Election;
import edu.flab.election.dto.ElectionRegisterRequestDto;
import edu.flab.election.dto.ElectionRegisterResponseDto;
import edu.flab.election.repository.CandidateMapper;
import edu.flab.election.repository.ElectionMapper;
import edu.flab.member.domain.Member;
import edu.flab.member.service.MemberFindService;
import edu.flab.rabbitmq.config.RabbitMqQueueName;
import edu.flab.rabbitmq.domain.RabbitMqSender;
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
	public ElectionRegisterResponseDto register(ElectionRegisterRequestDto dto) {
		Election election = saveElection(dto);
		Candidate host = addCandidateToElection(election, dto.getHostEmail());
		Candidate participant = addCandidateToElection(election, dto.getParticipantEmail());

		rabbitMqSender.send(new ElectionNotifyMessage(election, RabbitMqQueueName.ELECTION_REGISTER));

		return ElectionRegisterResponseDto.builder()
			.electionId(election.getId())
			.hostId(host.getId())
			.participantId(participant.getId())
			.build();
	}

	private Election saveElection(ElectionRegisterRequestDto dto) {
		Election election = Election.builder()
			.status(Election.ElectionStatus.PENDING)
			.contents(dto.getContents())
			.youtubeUrl(dto.getYoutubeUrl())
			.cost(dto.getCost())
			.createdAt(OffsetDateTime.now())
			.endedAt(OffsetDateTime.now().plus(Duration.ofMinutes(dto.getDurationMinute())))
			.build();

		electionMapper.save(election);
		return election;
	}

	private Candidate addCandidateToElection(Election election, String email) {
		Member member = memberFindService.findActiveMember(email);
		Candidate candidate = new Candidate(member.getId(), election.getId());
		election.addCandidate(candidate);
		candidateMapper.save(candidate);
		return candidate;
	}
}

