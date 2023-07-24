package edu.flab.member.domain;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class LolTierUtilTest {

	@ParameterizedTest
	@EnumSource(value = LolTier.Color.class, names = {"MASTER", "GRAND_MASTER", "CHALLENGER"})
	@DisplayName("상위 티어에 해당하는 티어는 [MASTER ~ CHALLENGER]이다")
	void test1(LolTier.Color color) {
		Assertions.assertThatNoException().isThrownBy(() -> LolTierUtil.createHighTier(color, 1000));
	}

	@Test
	@DisplayName("상위 티어에 해당하는 티어는 0점 이상의 랭크게임 포인트를 가질 수 있다")
	void test2() {
		Assertions.assertThatThrownBy(() -> LolTierUtil.createHighTier(LolTier.Color.MASTER, -1))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	@DisplayName("상위 티어에 해당하는 티어의 단계는 오직 0 하나 뿐이다")
	void test3() {
		LolTier master = LolTierUtil.createHighTier(LolTier.Color.MASTER, 500);
		Assertions.assertThat(master.getLevel()).isEqualTo(0);
	}

	@ParameterizedTest
	@EnumSource(value = LolTier.Color.class, names = {"IRON", "BRONZE", "SILVER", "GOLD", "PLATINUM", "DIAMOND"})
	@DisplayName("일반 티어에 해당하는 티어는 [IRON ~ DIAMOND]이다")
	void test4(LolTier.Color color) {
		Assertions.assertThatNoException().isThrownBy(() -> LolTierUtil.createNormalTier(color, 1, 100));
	}

	@Test
	@DisplayName("일반 티어에 해당하는 티어는 [0 ~ 100]점 사이의 랭크게임 포인트를 가질 수 있다")
	void test5() {
		assertThatThrownBy(() -> LolTierUtil.createNormalTier(LolTier.Color.IRON, 1, -10))
			.isInstanceOf(IllegalArgumentException.class);

		assertThatThrownBy(() -> LolTierUtil.createNormalTier(LolTier.Color.IRON, 1, 150))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	@DisplayName("일반 티어에 해당하는 티어는 [0 ~ 4] 사이의 단계를 가질 수 있다")
	void test6() {
		assertThatThrownBy(() -> LolTierUtil.createNormalTier(LolTier.Color.IRON, -1, 50))
			.isInstanceOf(IllegalArgumentException.class);

		assertThatThrownBy(() -> LolTierUtil.createNormalTier(LolTier.Color.IRON, 5, 50))
			.isInstanceOf(IllegalArgumentException.class);
	}
}
