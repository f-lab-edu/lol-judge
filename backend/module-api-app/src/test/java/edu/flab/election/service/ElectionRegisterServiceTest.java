package edu.flab.election.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.flab.election.domain.Candidate;
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
		Member writer = election.getMember();

		ElectionRegisterRequestDto dto = ElectionRegisterRequestDto.builder()
			.title(election.getTitle())
			.progressTime(election.getProgressTime())
			.youtubeUrl(election.getYoutubeUrl())
			.opinions(election.getCandidates().stream().map(Candidate::getOpinion).toList())
			.build();

		when(memberFindService.findActiveMember(writer.getEmail())).thenReturn(writer);
		when(electionJpaRepository.save(any(Election.class))).thenReturn(election);
		doNothing().when(rabbitMqSender).send(any(RabbitMqMessage.class));

		// when
		sut.register(writer.getEmail(), dto);

		// then
		verify(memberFindService).findActiveMember(writer.getEmail());
		verify(electionJpaRepository).save(any(Election.class));
		verify(rabbitMqSender).send(any(RabbitMqMessage.class));
	}
}
