package edu.flab.election.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.flab.election.domain.Candidate;
import edu.flab.election.domain.Election;
import edu.flab.election.domain.ElectionStatus;
import edu.flab.election.dto.ElectionRegisterRequestDto;
import edu.flab.election.dto.ElectionRegisterResponseDto;
import edu.flab.election.repository.ElectionJpaRepository;
import edu.flab.member.repository.MemberJpaRepository;
import edu.flab.rabbitmq.config.RabbitMqQueueName;
import edu.flab.rabbitmq.domain.RabbitMqSender;
import edu.flab.rabbitmq.message.RabbitMqMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ElectionRegisterService {
	private final MemberJpaRepository memberJpaRepository;
	private final RabbitMqSender rabbitMqSender;
	private final ElectionJpaRepository electionJpaRepository;

	@Transactional
	public ElectionRegisterResponseDto register(String hostEmail, ElectionRegisterRequestDto dto) {

		if (!memberJpaRepository.existsByEmail(hostEmail)) {
			throw new IllegalArgumentException("존재하지 않는 회원 이메일 입니다.");
		}

		Election election = Election.builder()
			.title(dto.getTitle())
			.status(ElectionStatus.IN_PROGRESS)
			.youtubeUrl(dto.getYoutubeUrl())
			.progressTime(dto.getProgressTime())
			.createdAt(OffsetDateTime.now())
			.endedAt(OffsetDateTime.now().plusHours(dto.getProgressTime()))
			.build();

		dto.getOpinions().forEach(o -> election.addCandidate(new Candidate(o)));

		electionJpaRepository.save(election);

		rabbitMqSender.send(new RabbitMqMessage<>(election.getId(), RabbitMqQueueName.ELECTION_REGISTER));

		log.info("재판이 생성되었습니다. <electionId={}><writer={}>", election.getId(), hostEmail);

		return ElectionRegisterResponseDto.builder()
			.electionId(election.getId())
			.writerEmail(hostEmail)
			.build();
	}
}

