package edu.flab.member.domain;

import static edu.flab.member.domain.LolTier.Color.*;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 재작성한 이유
 * 1. 기존 enum 형태일 때는 중복되는 정보가 많다.
 * - IRON1("IRON",1), IRON2("IRON",2), IRON3("IRON",3) => IRON 값이 중복된다.
 * - IRON1("IRON",1), BRONZE1("BRONZE",1), SILVER1("SILVER",1) => 레벨(숫자1) 값이 중복된다.
 * => 중복되는 정보를 클래스의 필드로 선언하여 개선하였다.
 * => 클라이언트 코드에서 클래스의 필드 값을 자유롭게 설정 가능하므로, 유효성 검사를 구현하였다.
 * 2. 회원들의 티어정보("IRON", "BRONZE", "SILVER" 등)를 비교해야하는데, 문자열 형태라 비교가 어려웠다.
 * => enum(IRON, BRONZE, SILVER, ...) 형태로 재정의했다.
 */

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
public class LolTier {
	/**
	 * 외부 정보를 내부 코드에서 관리할 때 변경 가능성을 고려하는 방법에 대한 고민
	 * [선택1] enum을 사용하면 입력에 제한을 둘 수 있다.
	 * 예를들어 IRON1, IRON2, IRON3, IRON4
	 * 티어그룹이 하나가 추가되는 경우, 선택2에 비해 많은 작업이 수반된다.
	 * 예를 들어, 에메랄드 티어 새로 생기는 경우, 에메랄드1 - 에메랄드4 티어를 추가해야한다.
	 * [선택2] class를 사용하여 확장 가능성을 열어둔다.
	 * 클래스에 color, level, 필드를 선언하고, 클라이언트코드에 입력을 맡긴다.
	 * 이 경우, 입력의 제한을 두기 위해 생성자에 유효성 검사 로직을 구현해야 한다.
	 *
	 */
	public enum Color {
		IRON, BRONZE, SILVER, GOLD, PLATINUM, DIAMOND, MASTER, GRAND_MASTER, CHALLENGER
	}

	public static final int NORMAL_TIER_MAX_LEVEL = 4;
	public static final int NORMAL_TIER_MIN_LEVEL = 1;
	public static final int NORMAL_TIER_MAX_POINT = 100;
	public static final int COMMON_TIER_MIN_POINT = 0;

	private Color color;
	private int level;
	private int point;

	private LolTier(Color color, Integer level, Integer point) {
		this.color = color;
		this.level = level;
		this.point = point;

		validateLevel();
		validatePoint();
	}

	public static LolTier normalTier(Color color, int level, int point) {
		return new LolTier(color, level, point);
	}

	public static LolTier highTier(Color color, int point) {
		return new LolTier(color, 0, point);
	}

	public boolean isNormalTier() {
		return color.compareTo(MASTER) < 0;
	}

	public boolean isHighTier() {
		return !isNormalTier();
	}

	private void validateLevel() {
		if (isNormalTier() && (level < NORMAL_TIER_MIN_LEVEL || level > NORMAL_TIER_MAX_LEVEL)) {
			throw new IllegalArgumentException(
				"MASTER 미만 티어의 레벨 범위는 [" + NORMAL_TIER_MIN_LEVEL + ", " + NORMAL_TIER_MAX_LEVEL + "] 입니다");
		}
	}

	private void validatePoint() {
		if (point < COMMON_TIER_MIN_POINT) {
			throw new IllegalArgumentException("포인트의 최소값은 [" + COMMON_TIER_MIN_POINT + "] 입니다");
		}
		if (isNormalTier() && point > NORMAL_TIER_MAX_POINT) {
			throw new IllegalArgumentException(
				"MASTER 미만 티어의 포인트 범위는 [" + COMMON_TIER_MIN_POINT + ", " + NORMAL_TIER_MAX_POINT + "] 입니다");
		}
	}
}

