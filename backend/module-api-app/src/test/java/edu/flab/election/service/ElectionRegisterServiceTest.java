package edu.flab.election.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.flab.election.domain.Candidate;
import edu.flab.election.domain.CandidateStatus;
import edu.flab.election.domain.Election;
import edu.flab.election.dto.ElectionRegisterRequestDto;
import edu.flab.election.repository.ElectionJpaRepository;
import edu.flab.member.TestFixture;
import edu.flab.member.domain.Member;
import edu.flab.member.service.MemberFindService;
import edu.flab.rabbitmq.domain.RabbitMqSender;
import edu.flab.rabbitmq.message.RabbitMqMessage;

@ExtendWith(MockitoExtension.class)
class ElectionRegisterServiceTest {

	@InjectMocks
	private ElectionRegisterService sut;

	@Mock
	private MemberFindService memberFindService;

	@Mock
	private ElectionJpaRepository electionJpaRepository;

	@Mock
	private RabbitMqSender rabbitMqSender;

	@Test
	@DisplayName("재판을 등록할 수 있다")
	void test1() {
		// given
		Election election = TestFixture.getElection();
		Candidate host = election.getCandidate(CandidateStatus.HOST);
		Candidate participant = election.getCandidate(CandidateStatus.PARTICIPANT);
		Member hostMember = host.getMember();
		Member participantMember = participant.getMember();

		ElectionRegisterRequestDto dto = ElectionRegisterRequestDto.builder()
			.cost(election.getCost())
			.opinion(host.getOpinion())
			.champion(host.getChampion())
			.participantEmail(participantMember.getEmail())
			.progressTime(election.getProgressTime())
			.youtubeUrl(election.getYoutubeUrl())
			.build();

		when(memberFindService.findActiveMember(hostMember.getEmail())).thenReturn(hostMember);
		when(memberFindService.findActiveMember(participantMember.getEmail())).thenReturn(participantMember);
		when(electionJpaRepository.save(any(Election.class))).thenReturn(election);
		doNothing().when(rabbitMqSender).send(any(RabbitMqMessage.class));

		// when
		sut.register(hostMember.getEmail(), dto);

		// then
		verify(memberFindService).findActiveMember(hostMember.getEmail());
		verify(memberFindService).findActiveMember(participantMember.getEmail());
		verify(electionJpaRepository).save(any(Election.class));
		verify(rabbitMqSender).send(any(RabbitMqMessage.class));
	}
}
