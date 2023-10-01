package edu.flab.election.service;

import static org.mockito.Mockito.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.flab.election.domain.Candidate;
import edu.flab.election.domain.Election;
import edu.flab.election.dto.ElectionDetailResponseDto;
import edu.flab.member.TestFixture;

@ExtendWith(MockitoExtension.class)
class ElectionDetailServiceTest {

	@InjectMocks
	private ElectionDetailService sut;

	@Mock
	private ElectionFindService electionFindService;

	@Test
	@DisplayName("재판의 자세한 정보를 조회할 수 있다")
	void test1() {
		// given
		Election election = TestFixture.getElection();

		when(electionFindService.findElection(anyLong())).thenReturn(election);

		// when
		ElectionDetailResponseDto electionDetail = sut.findElectionDetail(election.getId());

		// then
		ElectionDetailResponseDto expect = ElectionDetailResponseDto.builder()
			.id(election.getId())
			.writer(election.getMember().getGameAccount().getSummonerName())
			.title(election.getTitle())
			.youtubeUrl(election.getYoutubeUrl())
			.totalVotedCount(election.getTotalVotedCount())
			.createdAt(election.getCreatedAt())
			.endedAt(election.getEndedAt())
			.opinions(election.getCandidates().stream().map(Candidate::getOpinion).toList())
			.build();

		Assertions.assertThat(electionDetail).isEqualTo(expect);
	}
}
