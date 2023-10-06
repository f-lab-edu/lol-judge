package edu.flab.member.domain;

import java.util.NoSuchElementException;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class RankScore {

	private long score;
	
	public static RankScore zero() {
		return new RankScore(0L);
	}

	/**
	 * 회원의 랭킹은 rankScore 가 높은 순서로 정해진다.
	 * rankScore 는 [judgePoint, lolTier, id] 정보를 기반으로 계산된다.
	 * - 16자리 숫자로 구성
	 * - 큰 자릿수부터 순서대로 8자리, 2자리, 2자리, 4자리는 각각 judgePoint, LolTier.color, LolTier.level, LolTier.point 의 값으로 채워진다.
	 * 회원의 judgePoint, lolTier 정보가 변경될 때마다, 이 함수를 호출하여 rankScore 를 갱신한다.
	 */
	public static RankScore calc(Member member) {
		LolTier lolTier = member.getGameAccount().getLolTier();

		if (lolTier == null) {
			throw new NoSuchElementException("리그오브레전드 계정이 없는 회원은 랭킹에서 제외됩니다");
		}

		String judgePoint = String.format("%08d", member.getJudgePoint());
		String lolTierColor = String.format("%02d", lolTier.getColor().ordinal());
		String lolTierLevel = String.format("%02d", lolTier.getLevel().ordinal());
		String lolTierPoint = String.format("%04d", lolTier.getPoint());

		long totalScore = Long.parseLong(judgePoint + lolTierColor + lolTierLevel + lolTierPoint);

		return new RankScore(totalScore);
	}
}
