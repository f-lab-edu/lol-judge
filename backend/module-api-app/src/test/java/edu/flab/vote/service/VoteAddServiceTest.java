package edu.flab.vote.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.Duration;
import java.time.OffsetDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.flab.election.domain.Candidate;
import edu.flab.election.domain.Election;
import edu.flab.election.domain.ElectionStatus;
import edu.flab.election.dto.ElectionFindRequestDto;
import edu.flab.election.service.ElectionFindService;
import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberJudgePointCalcDto;
import edu.flab.member.service.MemberJudgePointUpdateService;
import edu.flab.vote.dto.VoteAddRequestDto;
import edu.flab.vote.repository.VoteMapper;

@ExtendWith(MockitoExtension.class)
class VoteAddServiceTest {

	@InjectMocks
	private VoteAddService sut;

	@Mock
	private VoteFindService voteFindService;

	@Mock
	private VoteMapper voteMapper;

	@Mock
	private ElectionFindService electionFindService;

	@Mock
	private MemberJudgePointUpdateService memberJudgePointUpdateService;

	// Test fixture
	private final Election election = Election.builder()
		.id(1L)
		.status(ElectionStatus.IN_PROGRESS)
		.youtubeUrl("7I5SKTY-JXc")
		.cost(100)
		.createdAt(OffsetDateTime.now())
		.endedAt(OffsetDateTime.now().plus(Duration.ofMinutes(60)))
		.build();
	private final Member member1 = Member.builder()
		.id(1L)
		.email("example1@example.com")
		.password("1234")
		.judgePoint(1000)
		.build();
	private final Member member2 = Member.builder()
		.id(2L)
		.email("example2@example.com")
		.password("1234")
		.judgePoint(1200)
		.build();
	private final Member member3 = Member.builder()
		.id(3L)
		.email("example3@example.com")
		.password("1234")
		.judgePoint(1100)
		.build();
	private final Candidate host = Candidate.builder()
		.id(1L)
		.memberId(member1.getId())
		.electionId(election.getId())
		.build();
	private final Candidate participant = Candidate.builder()
		.id(2L)
		.memberId(member2.getId())
		.electionId(election.getId())
		.build();

	@BeforeEach
	void setUp() {
		election.addCandidate(host);
		election.addCandidate(participant);
	}

	@Test
	@DisplayName("재판 후보자 중 한명에게 투표할 수 있다")
	void test1() {
		// given
		when(memberJudgePointUpdateService.minusJudgePoint(any(MemberJudgePointCalcDto.class))).thenReturn(member3);
		when(electionFindService.findElection(any(ElectionFindRequestDto.class))).thenReturn(election);
		when(voteFindService.hasVotedBefore(any())).thenReturn(false);

		// when
		VoteAddRequestDto voteDto = new VoteAddRequestDto(election.getId(), host.getId());
		sut.add(member3, voteDto);

		// then
		verify(memberJudgePointUpdateService).minusJudgePoint(any());
		verify(voteMapper).save(any());
	}

	@Test
	@DisplayName("포인트가 부족하면 투표할 수 없다")
	void test2() {
		// given
		member3.updateJudgePoint(0);
		when(electionFindService.findElection(any(ElectionFindRequestDto.class))).thenReturn(election);

		// when
		VoteAddRequestDto voteDto = new VoteAddRequestDto(election.getId(), host.getId());

		// then
		assertThatThrownBy(() -> sut.add(member3, voteDto)).isInstanceOf(IllegalStateException.class);
		verify(memberJudgePointUpdateService, never()).minusJudgePoint(any());
		verify(voteMapper, never()).save(any());
	}

	@Test
	@DisplayName("이미 투표했다면 다시 투표할 수 없다")
	void test3() {
		// given
		when(electionFindService.findElection(any(ElectionFindRequestDto.class))).thenReturn(election);
		when(voteFindService.hasVotedBefore(any())).thenReturn(true);

		// when
		VoteAddRequestDto voteDto = new VoteAddRequestDto(election.getId(), host.getId());

		// then
		assertThatThrownBy(() -> sut.add(member3, voteDto)).isInstanceOf(IllegalStateException.class);
		verify(memberJudgePointUpdateService, never()).minusJudgePoint(any());
		verify(voteMapper, never()).save(any());
	}

	@Test
	@DisplayName("투표 대상이 재판의 피고인 또는 고소인이 아닐 경우 예외가 발생한다")
	void test4() {
		// given
		when(electionFindService.findElection(any(ElectionFindRequestDto.class))).thenReturn(election);

		// when
		VoteAddRequestDto voteDto = new VoteAddRequestDto(election.getId(), 100L);

		// then
		assertThatThrownBy(() -> sut.add(member3, voteDto)).isInstanceOf(IllegalArgumentException.class);
		verify(memberJudgePointUpdateService, never()).minusJudgePoint(any());
		verify(voteMapper, never()).save(any());
	}

	@Test
	@DisplayName("피고인 또는 원고는 투표를 할 수 없다")
	void test5() {
		// given
		when(electionFindService.findElection(any(ElectionFindRequestDto.class))).thenReturn(election);

		// when
		VoteAddRequestDto voteDto = new VoteAddRequestDto(election.getId(), host.getId());

		// then
		assertThatThrownBy(() -> sut.add(member1, voteDto)).isInstanceOf(IllegalStateException.class);
		verify(memberJudgePointUpdateService, never()).minusJudgePoint(any());
		verify(voteMapper, never()).save(any());
	}
}
