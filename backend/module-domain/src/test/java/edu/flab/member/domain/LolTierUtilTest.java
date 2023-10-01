package edu.flab.member.domain;

import static edu.flab.member.domain.LolTier.*;
import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import edu.flab.member.domain.LolTier.Level;

class LolTierUtilTest {

	@ParameterizedTest
	@EnumSource(value = Color.class, names = {"MASTER", "GRAND_MASTER", "CHALLENGER"})
	@DisplayName("상위 티어에 해당하는 티어는 레벨이 1이고, 0점 이상의 랭크게임 포인트를 가질 수 있다")
	void test1(Color color) {
		Assertions.assertThatNoException().isThrownBy(() -> LolTierUtil.createTier(color, Level.I, 1000));
	}

	@ParameterizedTest
	@EnumSource(value = Level.class, names = {"NONE", "II", "III", "IV"})
	@DisplayName("상위 티어의 레벨이 1이 아니면 예외가 발생한다")
	void test2(Level wrongLevel) {
		Assertions.assertThatThrownBy(() -> LolTierUtil.createTier(Color.MASTER, wrongLevel, 1000))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@ParameterizedTest
	@ValueSource(ints = {-10, 100000})
	@DisplayName("상위 티어의 포인트가 0 이하 또는 10000 초과시 예외가 발생한다")
	void test3(int wrongPoint) {
		Assertions.assertThatThrownBy(() -> LolTierUtil.createTier(Color.MASTER, Level.I, wrongPoint))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@ParameterizedTest
	@ValueSource(ints = {-10, 150})
	@DisplayName("일반 티어에 해당하는 티어는 레벨이 1~4이고, [0 ~ 100]점 사이의 랭크게임 포인트를 가질 수 있다")
	void test4(int point) {
		assertThatThrownBy(() -> LolTierUtil.createTier(Color.IRON, Level.IV, point))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	@DisplayName("일반 티어의 레벨이 [1 ~ 4] 사이가 아니면 예외가 발생한다")
	void test5() {
		assertThatThrownBy(() -> LolTierUtil.createTier(Color.IRON, Level.NONE, 50))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@ParameterizedTest
	@ValueSource(ints = {-10, 100000})
	@DisplayName("일반 티어의 포인트가 0 이하 또는 100 초과시 예외가 발생한다")
	void test6(int wrongPoint) {
		assertThatThrownBy(() -> LolTierUtil.createTier(Color.IRON, Level.NONE, wrongPoint))
			.isInstanceOf(IllegalArgumentException.class);
	}
}
