package edu.flab.member.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import edu.flab.election.domain.Candidate;
import edu.flab.member.TestFixture;

class MemberTest {
	@Test
	@DisplayName("회원의 포인트를 0 이하로 변경하는 경우, 예외가 발생한다")
	void test1() {
		Member member = TestFixture.getMember();

		Assertions.assertThatThrownBy(() -> member.setJudgePoint(-10)).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	@DisplayName("회원의 보유 포인트가 10 미만인 경우, 투표를 하면 포인트 부족 예외가 발생한다")
	void test2() {
		Member member = TestFixture.getMember();

		Candidate candidate = TestFixture.getCandidate();

		Assertions.assertThatThrownBy(() -> member.vote(candidate)).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	@DisplayName("회원이 투표를 수행한 경우, 포인트가 10 차감된다")
	void test3() {
		// given
		Member member = TestFixture.getMember();
		member.setJudgePoint(100);

		Candidate candidate = TestFixture.getCandidate();

		// when
		member.vote(candidate);

		// then
		Assertions.assertThat(member.getJudgePoint()).isEqualTo(90);
	}

	@Test
	@DisplayName("회원이 투표를 수행한 경우, 대상 후보자는 투표자의 롤 랭크 티어에 비례하는 점수를 얻는다")
	void test4() {
		// given
		Member member = TestFixture.getMember(LolTierUtil.createTier(LolTier.Color.GOLD, LolTier.Level.III, 100));
		member.setJudgePoint(100);

		Candidate candidate = TestFixture.getCandidate();

		// when
		member.vote(candidate);

		// then
		Assertions.assertThat(candidate.getVotedScore()).isEqualTo(LolTier.Color.GOLD.getScore());
	}
}
