package edu.flab.vote.service;

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
import edu.flab.election.domain.Vote;
import edu.flab.election.repository.VoteJpaRepository;
import edu.flab.election.service.CandidateFindService;
import edu.flab.election.service.VoteFindService;
import edu.flab.member.TestFixture;
import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberJudgePointCalcDto;
import edu.flab.member.service.MemberJudgePointUpdateService;

@ExtendWith(MockitoExtension.class)
class VoteServiceTest {

	@InjectMocks
	private VoteService sut;

	@Mock
	private VoteFindService voteFindService;

	@Mock
	private VoteJpaRepository voteJpaRepository;

	@Mock
	private CandidateFindService candidateFindService;

	@Mock
	private MemberJudgePointUpdateService memberJudgePointUpdateService;

	@Test
	@DisplayName("재판 후보자 중 한명에게 투표할 수 있다")
	void test1() {
		// given
		Election election = TestFixture.getElection();

		Candidate candidate = election.getCandidates().get(0);

		Member voter = TestFixture.getMember();
		voter.setJudgePoint(100);

		Vote vote = new Vote();
		vote.setCandidate(candidate);
		vote.setMember(voter);

		when(candidateFindService.findById(anyLong())).thenReturn(candidate);
		when(memberJudgePointUpdateService.minusJudgePoint(any(MemberJudgePointCalcDto.class))).thenReturn(voter);
		when(voteFindService.hasVotedBefore(anyLong(), anyLong())).thenReturn(false);
		when(voteJpaRepository.save(any(Vote.class))).thenReturn(vote);

		// when
		sut.vote(voter.getId(), candidate.getId());

		// then
		verify(candidateFindService).findById(candidate.getId());
		verify(memberJudgePointUpdateService).minusJudgePoint(any());
		verify(voteFindService).hasVotedBefore(voter.getId(), candidate.getId());
		verify(voteJpaRepository).save(any(Vote.class));
	}

	@Test
	@DisplayName("포인트가 부족하면 투표할 수 없다")
	void test2() {
		// given
		Election election = TestFixture.getElection();

		Candidate candidate = election.getCandidates().get(0);

		Member voter = TestFixture.getMember();
		voter.setJudgePoint(0);

		Vote vote = new Vote();
		vote.setCandidate(candidate);
		vote.setMember(voter);

		when(candidateFindService.findById(anyLong())).thenReturn(candidate);

		// then
		Assertions.assertThatThrownBy(() -> sut.vote(voter.getId(), candidate.getId()))
			.isInstanceOf(
				IllegalStateException.class);
	}

	@Test
	@DisplayName("이미 투표했다면 다시 투표할 수 없다")
	void test3() {
		// given
		Election election = TestFixture.getElection();

		Candidate candidate = election.getCandidates().get(0);

		Member voter = TestFixture.getMember();
		voter.setJudgePoint(100);

		Vote vote = new Vote();
		vote.setCandidate(candidate);
		vote.setMember(voter);

		when(candidateFindService.findById(anyLong())).thenReturn(candidate);
		when(voteFindService.hasVotedBefore(anyLong(), anyLong())).thenReturn(true);

		// then
		Assertions.assertThatThrownBy(() -> sut.vote(voter.getId(), candidate.getId()))
			.isInstanceOf(
				IllegalStateException.class);
	}
}
