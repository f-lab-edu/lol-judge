package edu.flab.member.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
public sealed class LolTier permits LolTierUtil {
	public enum Color {
		IRON, BRONZE, SILVER, GOLD, PLATINUM, DIAMOND, MASTER, GRAND_MASTER, CHALLENGER
	}

	private Color color;
	private int level;
	private int point;

	protected LolTier(Color color, Integer level, Integer point) {
		this.color = color;
		this.level = level;
		this.point = point;
	}
}
