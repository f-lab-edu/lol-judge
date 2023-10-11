package edu.flab.vote.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.flab.election.config.VoteRule;
import edu.flab.election.domain.Candidate;
import edu.flab.election.domain.Election;
import edu.flab.election.service.CandidateFindService;
import edu.flab.election.service.VoteFindService;
import edu.flab.exception.BusinessException;
import edu.flab.member.TestFixture;
import edu.flab.member.domain.JudgePointHistoryType;
import edu.flab.member.domain.JudgePointHistory;
import edu.flab.member.domain.Member;
import edu.flab.member.service.MemberFindService;

@ExtendWith(MockitoExtension.class)
class VoteServiceTest {

	@InjectMocks
	private VoteService sut;

	@Mock
	private MemberFindService memberFindService;

	@Mock
	private VoteFindService voteFindService;

	@Mock
	private CandidateFindService candidateFindService;

	@Test
	@DisplayName("후보자 중 한명에게 투표할 수 있으며, 참여 비용 10포인트 차감된다")
	void test1() {
		// given
		Election election = TestFixture.getElection();

		Candidate candidate = election.getCandidates().get(0);

		Member member = TestFixture.getMember();
		member.setJudgePoint(100);

		when(memberFindService.findActiveMember(anyLong())).thenReturn(member);
		when(candidateFindService.findById(anyLong())).thenReturn(candidate);
		when(voteFindService.hasVotedBefore(anyLong(), anyLong())).thenReturn(false);

		// when
		sut.vote(1L, 1L);

		// then
		verify(memberFindService).findActiveMember(1L);
		verify(candidateFindService).findById(1L);
		verify(voteFindService).hasVotedBefore(1L, 1L);
		assertThat(member.getJudgePoint()).isEqualTo(90);
		assertThat(member.getJudgePointHistory().get(0))
			.isEqualTo(new JudgePointHistory(JudgePointHistoryType.VOTE,
				VoteRule.FEE));
	}

	@Test
	@DisplayName("회원이 보유한 포인트가 참여 포인트보다 부족하면 예외가 발생한다")
	void test2() {
		// given
		Election election = TestFixture.getElection();

		Candidate candidate = election.getCandidates().get(0);

		Member member = TestFixture.getMember();

		when(memberFindService.findActiveMember(anyLong())).thenReturn(member);
		when(candidateFindService.findById(anyLong())).thenReturn(candidate);
		when(voteFindService.hasVotedBefore(anyLong(), anyLong())).thenReturn(false);

		// then
		Assertions.assertThatThrownBy(() -> sut.vote(1L, 1L))
			.isInstanceOf(
				IllegalArgumentException.class);
	}

	@Test
	@DisplayName("이미 투표한 경우, 다시 투표하면 예외가 발생한다")
	void test3() {
		// given
		when(voteFindService.hasVotedBefore(anyLong(), anyLong())).thenReturn(true);

		// then
		Assertions.assertThatThrownBy(() -> sut.vote(1L, 1L))
			.isInstanceOf(BusinessException.class);
	}
}
