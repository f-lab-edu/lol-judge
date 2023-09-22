package edu.flab.election.service;

import static org.mockito.Mockito.*;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.flab.election.domain.Candidate;
import edu.flab.election.domain.CandidateStatus;
import edu.flab.election.domain.Election;
import edu.flab.election.dto.ElectionEditRequestDto;
import edu.flab.member.TestFixture;
import edu.flab.rabbitmq.domain.RabbitMqSender;

@ExtendWith(MockitoExtension.class)
class ElectionEditServiceTest {

	@InjectMocks
	private ElectionEditService sut;

	@Mock
	private ElectionFindService electionFindService;

	@Mock
	private RabbitMqSender rabbitMqSender;

	@Test
	@DisplayName("생성된 재판의 내용을 수정할 수 있다")
	void test1() {
		// given
		Election election = TestFixture.getElection();

		when(electionFindService.findElection(1L)).thenReturn(election);
		doNothing().when(rabbitMqSender).send(any());

		// when
		ElectionEditRequestDto dto = ElectionEditRequestDto.builder()
			.id(1L)
			.hostChampion("판테온")
			.hostOpinion("판테온은 상대 원딜을 노리는게 맞다")
			.participantChampion("징크스")
			.participantOpinion("판테온은 원딜을 지켜야한다")
			.cost(200)
			.build();

		sut.edit(dto);

		// then
		Candidate host = election.getCandidate(CandidateStatus.HOST);
		Candidate participant = election.getCandidate(CandidateStatus.PARTICIPANT);
		SoftAssertions.assertSoftly(softly -> {
			softly.assertThat(host.getChampion()).isEqualTo(dto.getHostChampion());
			softly.assertThat(host.getOpinion()).isEqualTo(dto.getHostOpinion());
			softly.assertThat(participant.getChampion()).isEqualTo(dto.getParticipantChampion());
			softly.assertThat(participant.getOpinion()).isEqualTo(dto.getParticipantOpinion());
			softly.assertThat(election.getCost()).isEqualTo(dto.getCost());
		});

		verify(electionFindService).findElection(anyLong());
		verify(rabbitMqSender).send(any());
	}
}
