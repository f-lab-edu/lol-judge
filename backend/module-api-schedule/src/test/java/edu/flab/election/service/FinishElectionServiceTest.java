package edu.flab.election.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.flab.election.domain.Election;
import edu.flab.member.TestFixture;
import edu.flab.member.domain.LolTier;
import edu.flab.member.domain.LolTierUtil;
import edu.flab.member.domain.Member;
import edu.flab.member.event.MemberRankScoreUpdateEventPublisher;

@ExtendWith(MockitoExtension.class)
class FinishElectionServiceTest {

	@InjectMocks
	private FinishElectionService sut;

	@Mock
	private MemberRankScoreUpdateEventPublisher publisher;

	@Test
	@DisplayName("투표 점수가 높은 후보를 승리로 판정하고, 승리 후보에 투표한 회원들에게 포인트를 지급한다")
	void test1() {
		// given
		Election election = TestFixture.getElection();

		Member winner = TestFixture.getMember(LolTierUtil.createTier(LolTier.Color.PLATINUM, LolTier.Level.II, 100));
		winner.setJudgePoint(100);
		winner.vote(election.getCandidates().get(0));

		Member loser = TestFixture.getMember(LolTierUtil.createTier(LolTier.Color.GOLD, LolTier.Level.III, 100));
		loser.setJudgePoint(100);
		loser.vote(election.getCandidates().get(1));

		doNothing().when(publisher).publishEvent(any(Member.class));

		// when
		sut.judge(election);

		// then
		assertThat(loser.getJudgePoint()).isEqualTo(90);
		assertThat(winner.getJudgePoint()).isEqualTo(110);
	}
}
