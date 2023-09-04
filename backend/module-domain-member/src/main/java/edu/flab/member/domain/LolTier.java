package edu.flab.member.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public sealed class LolTier permits LolTierUtil {

	@Getter
	public enum Color {
		IRON(1),
		BRONZE(2),
		SILVER(3),
		GOLD(4),
		PLATINUM(5),
		DIAMOND(6),
		MASTER(7),
		GRAND_MASTER(8),
		CHALLENGER(9);

		private final long score;

		Color(long score) {
			this.score = score;
		}
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
