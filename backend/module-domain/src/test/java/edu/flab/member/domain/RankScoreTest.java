package edu.flab.member.domain;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import edu.flab.member.TestFixture;

class RankScoreTest {

	@Test
	@DisplayName("리그오브레전드 계정이 존재하지 않으면 랭킹 점수 환산시 예외가 발생한다")
	void test1() {
		Member member = Member.builder()
			.email("user123@mail.com")
			.password("1234")
			.profileUrl("https://profile.link")
			.build();

		Assertions.assertThatThrownBy(() -> RankScore.calc(member)).isInstanceOf(NoSuchElementException.class);
	}

	@Test
	@DisplayName("두 유저의 롤 티어가 같을 때, 랭크는 JudgePoint 가 더 높은 순서로 결정된다")
	void test2() {
		Member member1 = TestFixture.getMember(LolTierUtil.createTier(LolTier.Color.GOLD, LolTier.Level.III, 100));
		Member member2 = TestFixture.getMember(LolTierUtil.createTier(LolTier.Color.GOLD, LolTier.Level.III, 100));

		member1.setJudgePoint(100);
		member2.setJudgePoint(90);

		RankScore rankScore1 = RankScore.calc(member1);
		RankScore rankScore2 = RankScore.calc(member2);

		Assertions.assertThat(rankScore1.compareTo(rankScore2)).isGreaterThan(0);
	}

	@MethodSource("provideTestCase")
	@ParameterizedTest
	@DisplayName("두 유저의 JudgePoint 가 같을 때, 랭크는 롤티어가 가 더 높은 순서로 결정된다")
	void test3(LolTier tier1, LolTier tier2) {
		Member member1 = TestFixture.getMember(tier1);
		Member member2 = TestFixture.getMember(tier2);

		member1.setJudgePoint(100);
		member2.setJudgePoint(100);

		RankScore rankScore1 = RankScore.calc(member1);
		RankScore rankScore2 = RankScore.calc(member2);

		Assertions.assertThat(rankScore1.compareTo(rankScore2)).isGreaterThan(0);
	}

	public static Stream<Arguments> provideTestCase() {
		return Stream.of(
			Arguments.of(LolTierUtil.createTier(LolTier.Color.GOLD, LolTier.Level.III, 95),
				LolTierUtil.createTier(LolTier.Color.GOLD, LolTier.Level.III, 8)),
			Arguments.of(LolTierUtil.createTier(LolTier.Color.GOLD, LolTier.Level.II, 40),
				LolTierUtil.createTier(LolTier.Color.GOLD, LolTier.Level.III, 40)),
			Arguments.of(LolTierUtil.createTier(LolTier.Color.GOLD, LolTier.Level.II, 40),
				LolTierUtil.createTier(LolTier.Color.GOLD, LolTier.Level.III, 40)),
			Arguments.of(LolTierUtil.createTier(LolTier.Color.GOLD, LolTier.Level.II, 40),
				LolTierUtil.createTier(LolTier.Color.GOLD, LolTier.Level.III, 99)),
			Arguments.of(LolTierUtil.createTier(LolTier.Color.PLATINUM, LolTier.Level.III, 40),
				LolTierUtil.createTier(LolTier.Color.GOLD, LolTier.Level.III, 40)),
			Arguments.of(LolTierUtil.createTier(LolTier.Color.PLATINUM, LolTier.Level.III, 40),
				LolTierUtil.createTier(LolTier.Color.GOLD, LolTier.Level.III, 70)),
			Arguments.of(LolTierUtil.createTier(LolTier.Color.PLATINUM, LolTier.Level.III, 40),
				LolTierUtil.createTier(LolTier.Color.GOLD, LolTier.Level.I, 70))
			);
	}
}
